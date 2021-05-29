/*
 * Generar reportes en segundo plano
 */
package com.guerra.simplepuntodeventa.controlador.reportes;

import com.guerra.simplepuntodeventa.modelo.EMFactory;
import com.guerra.simplepuntodeventa.recursos.componentes.animacion.DlgProceso;
import java.awt.Component;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Jaasiel
 */
public class GeneradorReporte extends SwingWorker<Void, String> {

    private final Component padre;
    private final RPT rpt;
    private final HashMap parametros;

    private final Connection con;
    private String path;
    private int option;

    // Enumerado para generar el reporte deseado
    public enum RPT {
        INVENTARIO("/reportes/rpt_inventario.jasper"),
        GANANCIAS("/reportes/rpt_ganancias.jasper"),
        ESTADO_CUENTA("/reportes/rpt_estado_cuenta.jasper"),
        MOVIMIENTOS("/reportes/rpt_movimiento.jasper"),
        VENTAS("/reportes/rpt_ventas.jasper"),
        COMPRAS("/reportes/rpt_compras.jasper"),
        INVENTARIO_BAJO("/reportes/rpt_inventario_bajo.jasper"),
        CREDITOS_PENDIENTES("/reportes/rpt_creditos_pendientes.jasper"),
        ARTICULOS("/reportes/rpt_articulos.jasper");

        private final String ruta;

        private RPT(String ruta) {
            this.ruta = ruta;
        }

        public String getRuta() {
            return ruta;
        }

    }

    /**
     * Generar reporte con parametros
     *
     * @param padre
     * @param rpt
     * @param parametros
     */
    public GeneradorReporte(Component padre,
            RPT rpt, HashMap parametros) {
        this.padre = padre;
        this.rpt = rpt;
        this.parametros = parametros;
        this.con = EMFactory.getConnectionMysql();
    }

    private void mensajeError(Exception e) {
        JOptionPane.showMessageDialog(padre, "OCURRIÓ UN ERROR AL GENERAR EL REPORTE:\n" + e.getMessage());
    }

    @Override
    protected Void doInBackground() throws Exception {

        JFileChooser save = new JFileChooser();
        save.setDialogTitle("Guardar Reporte PDF");
        FileFilter filter = new FileNameExtensionFilter("Archivo PDF", "pdf");
        save.setAcceptAllFileFilterUsed(false);
        save.setFileFilter(filter);

        option = save.showSaveDialog(padre);

        if (option == JFileChooser.APPROVE_OPTION) {

            publish("");

            path = save.getSelectedFile().getAbsoluteFile() + "";
            if (!path.substring(path.length() - 3, path.length()).equals("pdf")) {
                path = path + ".pdf";
            }

            generarReporte();
        }

        return null;
    }

    @Override
    protected void done() {
        SwingUtilities.invokeLater(() -> {
            DlgProceso.ocultarDlgProceso();
        });
        try {
            this.get();

            if (option == JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(padre, "Reporte generado con éxito.",
                        "Reporte", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (HeadlessException | InterruptedException | ExecutionException e) {
            mensajeError(e);
        }

    }

    @Override
    protected void process(List<String> chunks) {
        SwingUtilities.invokeLater(() -> {
            if (chunks.get(0).length() == 0) {
                DlgProceso.mostrarDlgProceso(padre);
            }
            DlgProceso.publicar(chunks.get(0));
        });
    }

    /**
     * Generar reporte
     */
    private void generarReporte() throws JRException, NullPointerException {

        publish("Obteniendo recursos...");
        URL reporteMaestro = getClass().getResource(this.rpt.getRuta());

        publish("Cargando reporte...");
        JasperReport reporteMaestroCompilado = (JasperReport) JRLoader.loadObject(reporteMaestro);

        publish("Llenando reporte...");
        JasperPrint reporteMaestroLleno = JasperFillManager.fillReport(reporteMaestroCompilado, this.parametros, con);

        publish("Exportando...");
        JasperExportManager.exportReportToPdfFile(reporteMaestroLleno, path);

    }
}
