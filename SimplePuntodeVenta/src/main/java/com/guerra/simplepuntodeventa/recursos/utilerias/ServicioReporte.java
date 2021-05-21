/*
 * Utileria de servicio para mandar a imprimir o exportar
 */
package com.guerra.simplepuntodeventa.recursos.utilerias;

import com.guerra.simplepuntodeventa.modelo.EMFactory;
import com.guerra.simplepuntodeventa.recursos.utilerias.modelo.ParametroReporte;
import java.awt.HeadlessException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author jaasi
 */
public class ServicioReporte {

    private static ServicioReporte reporteUtil;

    private ServicioReporte() {

    }

    public static ServicioReporte getInstancia() {
        if (reporteUtil == null) {
            reporteUtil = new ServicioReporte();
        }
        return reporteUtil;
    }

    /**
     * Imprimir un reporte de jasperReports en un servicio de impresion
     *
     * @param rutaReporteMaestro
     * @param parametros
     * @return <code>true</code> en caso de que todo salio correcto
     * @throws HeadlessException
     * @throws PrinterException
     * @throws IOException
     * @throws NullPointerException
     * @throws JRException
     */
    public boolean imprimir(String rutaReporteMaestro, ParametroReporte[] parametros) throws HeadlessException,
            PrinterException, IOException, NullPointerException, JRException {

        boolean exito = true;

        try {

            //servira para llenar los parametros
            Map p = new HashMap();

            //1. carga el reporte matestro
            URL reporteMaestro = getClass().getResource(rutaReporteMaestro);
            JasperReport reporteMaestroCompilado = (JasperReport) JRLoader.loadObject(reporteMaestro);

            //2.por cada parametro cargarlo al mapa de parametros segun tipo
            for (ParametroReporte pr : parametros) {

                if (pr.getTipo() == ParametroReporte.OBJETO) {
                    p.put(pr.getNombre(), pr.getValor());
                }

                if (pr.getTipo() == ParametroReporte.NOMBRE_SUB_RPT) {

                    URL subReporte = getClass().getResource(pr.getValor().toString());
                    JasperReport subReporteCompilado = (JasperReport) JRLoader.loadObject(subReporte);
                    p.put(pr.getNombre(), subReporteCompilado);
                }

            }

            byte[] pdf = JasperRunManager.runReportToPdf(reporteMaestroCompilado, p, EMFactory.getConnectionMysql());

            try (PDDocument document = PDDocument.load(pdf)) {
                PrinterJob printJob = PrinterJob.getPrinterJob();
                printJob.setPageable(new PDFPageable(document));
                exito = printJob.printDialog();
                if (exito) {
                    printJob.print();
                }
                document.close();
            }

        } catch (HeadlessException | PrinterException | IOException | NullPointerException | JRException e) {
            exito = false;
            throw e;
        }

        return exito;
    }
}
