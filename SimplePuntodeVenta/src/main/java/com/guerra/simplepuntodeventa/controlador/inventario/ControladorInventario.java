/*
 * Consultar inventario, realizar ajustes y ver movimientos
 */
package com.guerra.simplepuntodeventa.controlador.inventario;

import com.guerra.simplepuntodeventa.controlador.busqueda.ControladorBusquedaArticulo;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.dao.ArticuloDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.KardexDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;
import com.guerra.simplepuntodeventa.modelo.entidades.Kardex;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesArticulos;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesInventario;
import com.guerra.simplepuntodeventa.recursos.componentes.animacion.DlgProceso;
import com.guerra.simplepuntodeventa.recursos.componentes.modelos.ModeloJTextFieldCodigoBarra;
import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.FechaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.NumeroUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.inventario.IfrmMenuInventario;
import com.guerra.simplepuntodeventa.vista.inventario.PanAjustarInventario;
import com.guerra.simplepuntodeventa.vista.inventario.PanListInventario;
import com.guerra.simplepuntodeventa.vista.inventario.PanMovimientos;
import com.guerra.simplepuntodeventa.vista.inventario.VistaInventarioBean;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaasiel
 */
public class ControladorInventario {

    //constante para tipo de movimiento
    private static final String MOVIMIENTO[] = new String[]{
        "", "ENTRADA", "SALIDA", "AJUSTE", "DEVOLUCIÓN"
    };

    private Articulo articuloAJustar;

    private final PanAjustarInventario panAjustarInventario;
    private final PanMovimientos panMovimientos;
    private final PanListInventario panListInventario;
    private ControladorBusquedaArticulo busqueda;

    private final ArticuloDAOImpl articuloDAO = DAOManager.getInstancia().getArticuloDAO();
    private final KardexDAOImpl kardexDAO = DAOManager.getInstancia().getKardexDAO();

    public ControladorInventario(VistaInventarioBean bean) {
        this.panAjustarInventario = bean.getPanAjustarInventario();
        this.panListInventario = bean.getPanListInventario();
        this.panMovimientos = bean.getPanMovimientos();
        init();
    }

    private void init() {
        busqueda = new ControladorBusquedaArticulo(panAjustarInventario, (seleccionado) -> {
            setArticuloFormularioAjustarInventario(seleccionado);
        });
        panListInventario.txtBuscarArticulo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarArticulo();
            }
        });
        panAjustarInventario.txtCodigo.addKeyListener(new ModeloJTextFieldCodigoBarra());
        panAjustarInventario.txtDescripcion.setEditable(false);
        panAjustarInventario.txtCantidadActual.setEditable(false);
        panAjustarInventario.btnAjustar.addActionListener((e) -> {
            ajustarInventario();
        });
        panAjustarInventario.txtCodigo.addActionListener((e) -> {
            buscarArticuloCodigo();
        });
        panAjustarInventario.btnBuscar.addActionListener((e) -> {
            mostrarDlgBuscarArticulo();
        });
        panMovimientos.txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarArticuloNombreKardex();
            }

        });
        panMovimientos.btnIr.addActionListener((e) -> {
            consultarkardexRangoFecha();
        });
    }

    /**
     * llenar la tabla de articulos del dialogo de busqueda a partir de lista
     *
     * @param articulos
     * @param tabla
     */
    public static void llenaTablaArticulosDlgBusqueda(List<Articulo> articulos, JTable tabla) {
        TablaUtil.llenarTablaConEntity(tabla,
                articulos, new String[]{
                    "descripcion", "precioVenta", "cantidad"
                }, 0);
    }

    //calcular los totales del inventario
    private void calcularTotales() {
        panListInventario.txtInventarioTotal.setText(
                NumeroUtil.formatearConComas(FuncionesInventario.getSumaInventarioTotal())
        );
        panListInventario.txtCostoTotal.setText(
                NumeroUtil.formatearAMoneda(FuncionesInventario.getSumaCostoTotalInventario())
        );
        panListInventario.txtGananciaTotal.setText(
                NumeroUtil.formatearAMoneda(FuncionesInventario.getSumaGananciaTotalInventario())
        );
    }

    //llenar tabla del inventario
    private void llenarTablaInventario(List<Articulo> inventario) {
        TablaUtil.llenarTablaSinEntity(panListInventario.tblInventario,
                inventario, new String[]{
                    "cod1", "nombre", "cantidad", "minExistencia",
                    "precioCompra", "precioVenta", "porcentajeGanancia",
                    "ganancia", "minExistencia"
                }
        );
    }

    private void setArticuloFormularioAjustarInventario(Articulo articulo) {
        articuloAJustar = articulo;
        panAjustarInventario.txtCodigo.setText("");
        panAjustarInventario.txtDescripcion.setText(articulo.getDescripcion());
        panAjustarInventario.txtCantidadActual.setText(articulo.getCantidad() + "");
    }

    private void llenarTablaMovimientos(List<Kardex> movimientos) {
        DefaultTableModel modelo = (DefaultTableModel) panMovimientos.tblMovimientos.getModel();
        modelo.setRowCount(0);
        movimientos.forEach((k) -> {
            Object[] f = new Object[8];
            f[0] = k.getFecha();
            f[1] = k.getIdArticulo().getCod1();
            f[2] = k.getIdArticulo();
            f[3] = k.getConcepto();
            f[4] = k.getExistenciaAnterior();
            f[5] = MOVIMIENTO[k.getTipo()];
            f[6] = k.getCantidad();
            f[7] = k.getExistenciaPosterior();
            modelo.addRow(f);
        });
    }

    ///////////////////////metodos publicos///////////////////////
    public void consultarInventario() {
        SwingWorker<List<Articulo>, String> tarea = new SwingWorker<List<Articulo>, String>() {
            @Override
            protected List<Articulo> doInBackground() throws Exception {

                publish("");

                //consultar inventario de articulos con estado 1 activo
                List<Articulo> inventario = articuloDAO.readByNameQuery(
                        "Articulo.findByEstado", "estado", 1
                );

                return inventario;
            }

            @Override
            protected void done() {
                try {
                    SwingUtilities.invokeLater(() -> {
                        DlgProceso.ocultarDlgProceso();
                    });
                    List<Articulo> inventario = get();
                    llenarTablaInventario(inventario);
                    calcularTotales();
                    PanelUtil.cambiarPanel(IfrmMenuInventario.PAN_CAMBIANTE, panListInventario);
                } catch (InterruptedException | ExecutionException ex) {
                    MsjException.msjErrorEstandar(IfrmMenuInventario.PAN_CAMBIANTE, ex.getMessage());
                }
            }

            @Override
            protected void process(List<String> chunks) {
                SwingUtilities.invokeLater(() -> {
                    DlgProceso.mostrarDlgProceso(IfrmMenuInventario.PAN_CAMBIANTE);
                });
            }

        };
        tarea.execute();

    }

    public void consultarMovimientos() {

        SwingWorker<Void, String> tarea = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("");
                List<Kardex> movimientos = kardexDAO.readByNameQuery(
                        "Kardex.findByFecha", "fecha",
                        FechaUtil.formatearFechaTextoSqlite3(new Date())
                );
                llenarTablaMovimientos(movimientos);
                return null;
            }

            @Override
            protected void done() {
                IfrmMenuInventario.PROCESO.setVisible(false);
                PanelUtil.cambiarPanel(IfrmMenuInventario.PAN_CAMBIANTE, panMovimientos);
            }

            @Override
            protected void process(List<String> chunks) {
                if (!IfrmMenuInventario.PROCESO.isVisible()) {
                    IfrmMenuInventario.PROCESO.setVisible(true);
                }
            }

        };
        tarea.execute();

    }

    public void resetFormularioAjustarInventario() {
        panAjustarInventario.txtCodigo.setText("");
        panAjustarInventario.txtDescripcion.setText("");
        panAjustarInventario.txtCantidadActual.setText("");
        panAjustarInventario.spnNuevaCantidad.setValue(0);
        panAjustarInventario.txtConcepto.setText("Ajuste de inventario");
        articuloAJustar = null;
    }

    ///////////////eventos////////////////////////
    private void buscarArticulo() {
        //buscar un articulo por nombre y estado 1
        List<Articulo> inventario = FuncionesArticulos
                .buscarArticulosPorNombreEstado(
                        1, panListInventario.txtBuscarArticulo.getText().trim()
                );
        llenarTablaInventario(inventario);

    }

    private void ajustarInventario() {//realizar el ajuste
        /////////validacion//////////
        if (articuloAJustar == null) {
            MsjInfo.msjNoArticuloBuscado(panAjustarInventario);
        } else if (ValidarJTextField.campoVacio(panAjustarInventario.txtConcepto)) {
            MsjValidacion.msjJTextFieldRequeridos(panAjustarInventario);
        } else {/////////formulario valido///////////

            //datos para el kardex
            int cantidadActual = articuloAJustar.getCantidad();
            int nuevaCantidad = (int) panAjustarInventario.spnNuevaCantidad.getValue();
            int diferencia = NumeroUtil.calcularDiferencia(cantidadActual, nuevaCantidad);

            articuloAJustar.setCantidad((int) panAjustarInventario.spnNuevaCantidad.getValue());

            try {
                articuloDAO.update(articuloAJustar);

                boolean registroKardex = FuncionesInventario.registrarKardex(
                        panAjustarInventario.txtConcepto.getText(), cantidadActual,
                        FuncionesInventario.MOVIMIENTO_AJUSTE,
                        diferencia, nuevaCantidad, articuloAJustar
                );

                if (registroKardex) {
                    resetFormularioAjustarInventario();
                    MsjInfo.msjActualizacionExitosa(panAjustarInventario);
                }
            } catch (Exception e) {
                MsjException.msjErrorActualizar(panAjustarInventario, e.getMessage());
            }
        }
    }

    private void buscarArticuloCodigo() {
        String codigo = panAjustarInventario.txtCodigo.getText().toUpperCase();
        Articulo articulo = FuncionesInventario.getArticuloPorCodigo(codigo);
        if (articulo != null) {//hubieron resultados
            setArticuloFormularioAjustarInventario(articulo);
        } else {// sino pues se indica al usuario
            MsjInfo.msjNoResultadoCodigo(panAjustarInventario, codigo);
        }
    }

    private void mostrarDlgBuscarArticulo() {
        busqueda.mostrarDialogoBuscarArticulo();
    }

    private void buscarArticuloNombreKardex() {
        //buscar un articulo por nombre en el kardex
        List<Kardex> movimientos = FuncionesInventario
                .buscarArticuloKarde(panMovimientos.txtBuscar.getText());
        llenarTablaMovimientos(movimientos);
    }

    private void consultarkardexRangoFecha() {
        if (panMovimientos.fechaMin.getDate() != null
                && panMovimientos.fechaMax.getDate() != null) {
            List<Kardex> movimientos = FuncionesInventario.filtrarKardexRangoFecha(
                    panMovimientos.fechaMin.getDate(), panMovimientos.fechaMax.getDate()
            );
            llenarTablaMovimientos(movimientos);
        }
    }
}
