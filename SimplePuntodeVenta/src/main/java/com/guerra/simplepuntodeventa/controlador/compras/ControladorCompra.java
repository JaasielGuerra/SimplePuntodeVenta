/*
 * Controlador para manejar compras
 */
package com.guerra.simplepuntodeventa.controlador.compras;

import com.guerra.simplepuntodeventa.controlador.articulos.ControladorArticulos;
import com.guerra.simplepuntodeventa.controlador.busquedaarticulo.ControladorBusquedaArticulo;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.ArticuloDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.DetalleCompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;
import com.guerra.simplepuntodeventa.modelo.entidades.Compra;
import com.guerra.simplepuntodeventa.modelo.entidades.DetalleCompra;
import com.guerra.simplepuntodeventa.modelo.entidades.Proveedor;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesCompras;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesInventario;
import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.recursos.componentes.modelos.ModeloJTextFieldCodigoBarra;
import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.FechaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.NumeroUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJSpinner;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.compras.DlgCancelarCompra;
import com.guerra.simplepuntodeventa.vista.compras.DlgDetalleCompra;
import com.guerra.simplepuntodeventa.vista.compras.DlgNuevoArticulo;
import com.guerra.simplepuntodeventa.vista.compras.IfrmMenuCompras;
import com.guerra.simplepuntodeventa.vista.compras.PanListCompras;
import com.guerra.simplepuntodeventa.vista.compras.PanNuevaCompra;
import com.guerra.simplepuntodeventa.vista.compras.VistaCompraBean;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaasiel
 */
public class ControladorCompra {

    private Articulo articuloTemp;
    private Compra compraTemp;

    private final PanListCompras panListCompras;
    private final PanNuevaCompra panNuevaCompra;
    private final DlgNuevoArticulo dlgNuevoArticulo;
    private final DlgDetalleCompra dlgDetalleCompra;
    private final DlgCancelarCompra dlgCancelarCompra;

    private ControladorBusquedaArticulo busqueda;
    private ControladorArticulos controladorArticulos;

    private CompraDAOImpl compraDAO = DAOManager.getInstancia().getCompraDAO();
    private DetalleCompraDAOImpl detalleCompraDAO = DAOManager.getInstancia().getDetalleCompraDAO();
    private ArticuloDAOImpl articuloDAO = DAOManager.getInstancia().getArticuloDAO();

    public ControladorCompra(VistaCompraBean bean) {
        this.panListCompras = bean.getPanListCompras();
        this.panNuevaCompra = bean.getPanNuevaCompra();
        this.dlgNuevoArticulo = new DlgNuevoArticulo(null, true);
        this.controladorArticulos = new ControladorArticulos(dlgNuevoArticulo, (a) -> {
            dlgNuevoArticulo.dispose();
            setArticuloDetalle(a);
        });
        this.dlgDetalleCompra = new DlgDetalleCompra(null, true);
        this.dlgCancelarCompra = new DlgCancelarCompra(null, true);
        init();
    }

    private void init() {
        panNuevaCompra.ftdTotal.setValue(0.00D);
        panNuevaCompra.txtDescripcion.setEnabled(false);
        panNuevaCompra.txtExistenciaActual.setEnabled(false);
        panNuevaCompra.spnCantidad.setEnabled(false);
        panNuevaCompra.spnPCompra.setEnabled(false);
        panNuevaCompra.spnPVenta.setEnabled(false);

        panNuevaCompra.txtCodigoArticulo.addKeyListener(new ModeloJTextFieldCodigoBarra());

        busqueda = new ControladorBusquedaArticulo(panNuevaCompra, (seleccionado) -> {
            setArticuloDetalle(seleccionado);
        });
        panNuevaCompra.txtCodigoArticulo.addActionListener((e) -> {
            buscarArticuloCodigo();
        });
        panNuevaCompra.btnBuscarArticulo.addActionListener((e) -> {
            mostrarVentanaBuscarArticulo();
        });
        panNuevaCompra.btnRealizarCompra.addActionListener((e) -> {
            realizarCompra();
        });
        panNuevaCompra.btnAgregarDetalle.addActionListener((e) -> {
            agregarDetalleTabla();
        });
        panNuevaCompra.btnQuitarDetalle.addActionListener((e) -> {
            quitarDetalleTabla();
        });
        panNuevaCompra.btnNuevoArticulo.addActionListener((e) -> {
            dialogoNuevoArticulo();
        });
        ComboBoxUtil.llenarComboEstadoCompraVenta(panListCompras.cbxEstado);
        panListCompras.cbxEstado.addActionListener((e) -> {
            consutarComprasCbxEstado();
        });
        panListCompras.btnDetalles.addActionListener((e) -> {
            verDetalleCompra();
        });
        panListCompras.txtBuscarFactura.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarCompraNoFactura();
            }
        });
        panListCompras.btnIr.addActionListener((e) -> {
            filtrarComprasRangoFecha();
        });
        panListCompras.btnCancelarCompra.addActionListener((e) -> {
            mostrarDlgCancelarCompra();
        });
        dlgCancelarCompra.btnConfirmar.addActionListener((e) -> {
            confirmarCancelarCompra();
        });
        dlgCancelarCompra.proceso.setVisible(false);
    }

    //realizar una compra y registrar inventario
    private void procederRealizarCompra(Compra compra, List<DetalleCompra> detalle, String concepto) {

        SwingWorker<String, String> tarea = new SwingWorker<String, String>() {
            @Override
            protected String doInBackground() throws Exception {
                publish("");

                try {

                    // 1. registrar compra
                    compraDAO.create(compra);

                    // recorrer la lista para registrar cada detalle de compra, y actualizar inventario
                    for (DetalleCompra d : detalle) {

                        // 2. registrar detalle
                        d.setIdCompra(compra);
                        detalleCompraDAO.create(d);

                    }

                } catch (Exception e) {
                    return e.getMessage();
                }

                return "true";
            }

            @Override
            protected void done() {
                try {
                    IfrmMenuCompras.PROCESO.setVisible(false);
                    panNuevaCompra.btnRealizarCompra.setEnabled(true);

                    // si termina con un true, todo ok
                    if ("true".equals(this.get())) {
                        resetFormularioCompra();
                        MsjInfo.msjRegistroExitoso(panNuevaCompra);
                    } else {
                        MsjException.msjErrorGuardar(panNuevaCompra, this.get());
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    MsjException.msjErrorEstandar(panNuevaCompra, ex.getMessage());
                }

            }

            @Override
            protected void process(List<String> chunks) {
                if (!IfrmMenuCompras.PROCESO.isVisible()) {
                    IfrmMenuCompras.PROCESO.setVisible(true);
                    panNuevaCompra.btnRealizarCompra.setEnabled(false);
                }
            }

        };
        tarea.execute();

    }

    // cancela una compra y da salida al inventario
    private void procederCancelarCompra(Compra c, String motivo) {
        SwingWorker<String, String> tarea = new SwingWorker<String, String>() {
            @Override
            protected String doInBackground() throws Exception {
                publish("");

                try {

                    // 1. Cambiar estado de compra -> 0 cancelada
                    c.setEstado(0);
                    compraDAO.update(c);

                    //2. ahora por cada detalle se actualiza su estado
                    for (DetalleCompra d : c.getDetalleCompraList()) {
                        d.setEstado(0);// eliminado
                        detalleCompraDAO.update(d);
                    }

                } catch (Exception e) {
                    return e.getMessage();
                }

                return "true";
            }

            @Override
            protected void done() {

                try {
                    dlgCancelarCompra.proceso.setVisible(false);
                    dlgCancelarCompra.btnConfirmar.setEnabled(true);

                    // si es true todo ok
                    if ("true".equals(this.get())) {
                        resetFormularioCancelarCompra();
                        MsjInfo.msjExitoEstandar(dlgCancelarCompra);
                        dlgCancelarCompra.dispose();
                        consultarCompras();

                    } else {
                        MsjException.msjErrorEstandar(dlgCancelarCompra, this.get());
                    }

                } catch (InterruptedException | ExecutionException e) {
                    MsjException.msjErrorEstandar(dlgCancelarCompra, e.getMessage());
                }

            }

            @Override
            protected void process(List<String> chunks) {
                if (!dlgCancelarCompra.proceso.isVisible()) {
                    dlgCancelarCompra.proceso.setVisible(true);
                    dlgCancelarCompra.btnConfirmar.setEnabled(false);
                }
            }

        };
        tarea.execute();
    }

    private void setArticuloDetalle(Articulo a) {
        panNuevaCompra.txtDescripcion.setEnabled(true);
        panNuevaCompra.txtExistenciaActual.setEnabled(true);
        panNuevaCompra.spnCantidad.setEnabled(true);
        panNuevaCompra.spnPCompra.setEnabled(true);
        panNuevaCompra.spnPVenta.setEnabled(true);
        articuloTemp = a;
        panNuevaCompra.txtDescripcion.setText(a.getDescripcion());
        panNuevaCompra.txtExistenciaActual.setText(a.getCantidad() + "");
        panNuevaCompra.spnPCompra.setValue(a.getPrecioCompra());
        panNuevaCompra.spnPVenta.setValue(a.getPrecioVenta());
        panNuevaCompra.txtCodigoArticulo.setText("");
    }

    //valida si el detalle existe, y actualiza sus datos, en caso contrario agrega nuevo
    private void buscarCoincidenciasDetalleTabla(JTable tablaDetalle, DetalleCompra d) {

        DefaultTableModel m = (DefaultTableModel) tablaDetalle.getModel();
        boolean coincidencia = false;

        // si esta vacio se agrega fila
        if (m.getRowCount() == 0) {

            agregarFilaDetalleATablaNuevaCompra(m, d);

        } else {//sino se busca una coincidencia
            for (int i = 0; i < m.getRowCount(); i++) {

                // obtener detalle de la tabla
                DetalleCompra dtActual = (DetalleCompra) m.getValueAt(i, 0);

                // validar coincidencia con el id del articulo
                if (dtActual.getIdArticulo().getIdArticulo().equals(d.getIdArticulo().getIdArticulo())) {

                    coincidencia = true;

                    // actualizar detalle en la tabla
                    int nuevaCantidad = dtActual.getCantidad() + d.getCantidad();
                    double nuevoSubtotal = NumeroUtil.redondear(d.getPrecioUnitario() * nuevaCantidad, 2);

                    d.setCantidad(nuevaCantidad);
                    d.setSubTotal(nuevoSubtotal);

                    m.setValueAt(d, i, 0);
                    m.setValueAt(d.getPrecioUnitario(), i, 2);
                    m.setValueAt(d.getCantidad(), i, 3);
                    m.setValueAt(d.getSubTotal(), i, 4);
                    break;
                }

            }

            if (!coincidencia) {//si no hay coincidencias, pues agregar fila nueva
                agregarFilaDetalleATablaNuevaCompra(m, d);
            }
        }

    }

    //agregar una fila nueva con un detalle a la tabla de detalle de compra
    private void agregarFilaDetalleATablaNuevaCompra(DefaultTableModel m, DetalleCompra d) {
        Object[] f = new Object[5];
        f[0] = d;
        f[1] = d.getIdArticulo().getDescripcion();
        f[2] = d.getPrecioUnitario();
        f[3] = d.getCantidad();
        f[4] = d.getSubTotal();

        m.addRow(f);
    }

    //limpia el formulario para agregar detalle a la tabla
    private void limpiarFormularioDetalleCompra() {
        panNuevaCompra.txtDescripcion.setEnabled(false);
        panNuevaCompra.txtExistenciaActual.setEnabled(false);
        panNuevaCompra.spnCantidad.setEnabled(false);
        panNuevaCompra.spnPCompra.setEnabled(false);
        panNuevaCompra.spnPVenta.setEnabled(false);
        panNuevaCompra.txtCodigoArticulo.setText("");
        panNuevaCompra.txtDescripcion.setText("");
        panNuevaCompra.txtExistenciaActual.setText("");
        ValidarJSpinner.resetValuesDouble(new JSpinner[]{
            panNuevaCompra.spnPCompra,
            panNuevaCompra.spnPVenta
        }, 0.00D);
        ValidarJSpinner.resetValuesInteger(new JSpinner[]{
            panNuevaCompra.spnCantidad
        }, 0);
        articuloTemp = null;
    }

    private void calcularTotalCompra(JTable tablaDetallesCompra) {
        DefaultTableModel m = (DefaultTableModel) tablaDetallesCompra.getModel();

        int nFilas = m.getRowCount();
        double total = 0.00D;

        for (int i = 0; i < nFilas; i++) {
            total += (double) m.getValueAt(i, 4);
        }
        panNuevaCompra.ftdTotal.setValue(NumeroUtil.redondear(total, 2));
    }

    //llenar tabla de articulos a partir de una lista
    private void llenarTablaCompras(List<Compra> compras) {
        TablaUtil.llenarTablaConEntity(panListCompras.tblCompras, compras, new String[]{
            "idCompra", "fecha", "noFactura", "serieFactura", "total", "estado"
        }, 0);
    }

    private void resetFormularioCancelarCompra() {
        ValidarJTextField.resetValues(new JTextField[]{
            dlgCancelarCompra.txtConcepto
        });
    }

    //////////metodos publicos//////////////////
    public void resetFormularioCompra() {
        limpiarFormularioDetalleCompra();
        ValidarJTextField.resetValues(new JTextField[]{
            panNuevaCompra.txtNoFactura, panNuevaCompra.txtSerie,
            panNuevaCompra.txtConcepto
        });
        DefaultTableModel model = (DefaultTableModel) panNuevaCompra.tblDetalles.getModel();
        model.setRowCount(0);
        panNuevaCompra.ftdTotal.setValue(0.00D);
    }

    public void consultarCompras() {
        SwingWorker<Void, String> tarea = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("");
                List<Compra> compras = compraDAO.readByNameQuery("Compra.findByEstado", "estado", 1);
                llenarTablaCompras(compras);
                return null;
            }

            @Override
            protected void done() {
                IfrmMenuCompras.PROCESO.setVisible(false);
                PanelUtil.cambiarPanel(IfrmMenuCompras.PANEL_CAMBIANTE, panListCompras);
            }

            @Override
            protected void process(List<String> chunks) {
                if (!IfrmMenuCompras.PROCESO.isVisible()) {
                    IfrmMenuCompras.PROCESO.setVisible(true);
                }
            }

        };
        tarea.execute();

    }

    //////////////////eventos/////////////////
    private void buscarArticuloCodigo() {

        if (!panNuevaCompra.txtCodigoArticulo.getText().isEmpty()) {

            Articulo a = FuncionesInventario.getArticuloPorCodigo(
                    panNuevaCompra.txtCodigoArticulo.getText()
            );

            if (a != null) {
                setArticuloDetalle(a);
            } else {
                MsjInfo.msjNoResultadoCodigo(panNuevaCompra,
                        panNuevaCompra.txtCodigoArticulo.getText());
            }

            panNuevaCompra.txtCodigoArticulo.setText("");
        }
    }

    private void mostrarVentanaBuscarArticulo() {
        busqueda.mostrarDialogoBuscarArticulo();
    }

    private void realizarCompra() {
        ///validar formulario
        if (ValidarJTextField.camposVacios(
                new JTextField[]{
                    panNuevaCompra.txtNoFactura,
                    panNuevaCompra.txtSerie
                }
        )) {

            MsjValidacion.msjJTextFieldRequeridos(panNuevaCompra);

        } else if (panNuevaCompra.tblDetalles.getRowCount() == 0) {
            MsjValidacion.msjTablaVaciaSinArticulos(panNuevaCompra);

        } else {//formulario valido

            //////////registrar la compra///////////
            Compra c = new Compra();
            c.setFecha(FechaUtil.formatearFechaTextoSqlite3(new Date()));
            c.setNoFactura(panNuevaCompra.txtNoFactura.getText().trim());
            c.setSerieFactura(panNuevaCompra.txtSerie.getText().trim());
            c.setTotal(NumeroUtil.redondear((double) panNuevaCompra.ftdTotal.getValue(), 2));
            c.setIdProveedor((Proveedor) Session.getInstancia().getAttribute("proveedor"));// proveedor por defecto
            c.setEstado(1);// realizada
            c.setIdUsuario((Usuario) Session.getInstancia().getAttribute("user"));
            c.setFechaCommit(new Date());
            c.setHoraCommit(new Date());
            c.setSaldo(null);
            c.setTipoCompra(1);//al contado

            DefaultTableModel m = (DefaultTableModel) panNuevaCompra.tblDetalles.getModel();
            int rowCount = m.getRowCount();
            List<DetalleCompra> detalleCompraList = new ArrayList<>();
            for (int i = 0; i < rowCount; i++) {

                //recorrer tabla detalles de compra y obtener el entity
                detalleCompraList.add((DetalleCompra) m.getValueAt(i, 0));

            }

            procederRealizarCompra(c, detalleCompraList, panNuevaCompra.txtConcepto.getText().trim());

        }
    }

    private void agregarDetalleTabla() {

        //validar antes de agregar
        if (articuloTemp == null) {
            MsjInfo.msjNoArticuloBuscado(panNuevaCompra);
        } else if (!ValidarJSpinner.valorDoubleMayorN(0, panNuevaCompra.spnPCompra)
                || !ValidarJSpinner.valorDoubleMayorN(0, panNuevaCompra.spnPVenta)
                || !ValidarJSpinner.valorIntegerMayorN(0, panNuevaCompra.spnCantidad)) {
            MsjValidacion.msjValoresNumericosInvalidos(panNuevaCompra);
        } else {

            //actualizo los precios y ganancia por si a caso cambiaron
            articuloTemp.setPrecioCompra((double) panNuevaCompra.spnPCompra.getValue());
            articuloTemp.setPrecioVenta((double) panNuevaCompra.spnPVenta.getValue());
            Double ganancia = articuloTemp.getPrecioVenta() - articuloTemp.getPrecioCompra();
            Double porcentajeGanancia = (ganancia / articuloTemp.getPrecioCompra()) * 100;
            articuloTemp.setPorcentajeGanancia(NumeroUtil.redondear(porcentajeGanancia, 2));
            articuloTemp.setGanancia(NumeroUtil.redondear(ganancia, 2));

            /////////nuevo detalle//////////////// 
            DetalleCompra d = new DetalleCompra();
            d.setCantidad((int) panNuevaCompra.spnCantidad.getValue());
            d.setPrecioUnitario((double) panNuevaCompra.spnPCompra.getValue());
            d.setSubTotal(NumeroUtil.redondear(d.getCantidad() * d.getPrecioUnitario(), 2));
            d.setIdArticulo(articuloTemp);
            d.setEstado(1);//realizado
            d.setIdUsuario((Usuario) Session.getInstancia().getAttribute("user"));
            buscarCoincidenciasDetalleTabla(panNuevaCompra.tblDetalles, d);
            limpiarFormularioDetalleCompra();
            calcularTotalCompra(panNuevaCompra.tblDetalles);
        }

    }

    private void quitarDetalleTabla() {

        int filaSeleccionada = panNuevaCompra.tblDetalles.getSelectedRow();

        if (filaSeleccionada > -1) {
            DefaultTableModel m = (DefaultTableModel) panNuevaCompra.tblDetalles.getModel();
            m.removeRow(filaSeleccionada);
            calcularTotalCompra(panNuevaCompra.tblDetalles);
        } else {
            MsjInfo.msjSeleccioneFila(panNuevaCompra);
        }
    }

    private void dialogoNuevoArticulo() {
        controladorArticulos.cargarCombosCaracteristicasNuevoArticulo();
        controladorArticulos.resetValuesNuevoArticulo();
        dlgNuevoArticulo.setLocationRelativeTo(panNuevaCompra);
        dlgNuevoArticulo.setVisible(true);
    }

    private void consutarComprasCbxEstado() {

        SwingWorker<Void, String> tarea = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("");
                Estado e = (Estado) panListCompras.cbxEstado.getSelectedItem();
                List<Compra> compras = compraDAO.readByNameQuery("Compra.findByEstado", "estado", e.getValor());
                llenarTablaCompras(compras);
                return null;
            }

            @Override
            protected void done() {
                IfrmMenuCompras.PROCESO.setVisible(false);
            }

            @Override
            protected void process(List<String> chunks) {
                if (!IfrmMenuCompras.PROCESO.isVisible()) {
                    IfrmMenuCompras.PROCESO.setVisible(true);
                }
            }

        };
        tarea.execute();

    }

    private void verDetalleCompra() {
        Compra c = TablaUtil.getEntityFilaSeleccionada(panListCompras.tblCompras, 0, Compra.class);

        if (c != null) {

            List<DetalleCompra> detalles = FuncionesCompras.obtenerDetalleCompra(c);

            TablaUtil.llenarTablaSinEntity(dlgDetalleCompra.tblDetalle, detalles, new String[]{
                "idArticulo.cod1", "idArticulo.descripcion", "cantidad", "precioUnitario", "subTotal"
            });
            dlgDetalleCompra.lblFechaCompra.setText("Fecha: ".concat(c.getFecha()));
            dlgDetalleCompra.ftdTotal.setValue(c.getTotal());
            dlgDetalleCompra.setLocationRelativeTo(panListCompras);
            dlgDetalleCompra.setVisible(true);
        } else {
            MsjInfo.msjSeleccioneFila(panListCompras);
        }
    }

    private void buscarCompraNoFactura() {
        List<Compra> compras = FuncionesCompras.buscarCompraNoFactura(
                panListCompras.txtBuscarFactura.getText().trim()
        );
        llenarTablaCompras(compras);
    }

    private void filtrarComprasRangoFecha() {

        Date min = panListCompras.fechaMin.getDate();
        Date max = panListCompras.fechaMax.getDate();

        if (min != null && max != null) {
            SwingWorker<Void, String> tarea = new SwingWorker<Void, String>() {
                @Override
                protected Void doInBackground() throws Exception {
                    publish("");
                    List<Compra> compras = FuncionesCompras.filtrarComprasRangoFecha(
                            panListCompras.fechaMin.getDate(), panListCompras.fechaMax.getDate()
                    );
                    llenarTablaCompras(compras);
                    return null;
                }

                @Override
                protected void done() {
                    IfrmMenuCompras.PROCESO.setVisible(false);
                }

                @Override
                protected void process(List<String> chunks) {
                    if (!IfrmMenuCompras.PROCESO.isVisible()) {
                        IfrmMenuCompras.PROCESO.setVisible(true);
                    }
                }

            };
            tarea.execute();
        }

    }

    private void mostrarDlgCancelarCompra() {

        compraTemp = TablaUtil.getEntityFilaSeleccionada(panListCompras.tblCompras, 0, Compra.class);

        if (compraTemp != null) {
            if (compraTemp.getEstado() != 0) {

                resetFormularioCancelarCompra();
                dlgCancelarCompra.setLocationRelativeTo(panListCompras);
                dlgCancelarCompra.setVisible(true);
            } else {
                MsjInfo.msjYaCancelado(panListCompras);
            }
        } else {
            MsjInfo.msjSeleccioneFila(panListCompras);
        }

    }

    private void confirmarCancelarCompra() {
        //validar el concepto
//        if (ValidarJTextField.campoVacio(dlgCancelarCompra.txtConcepto)) {

//            MsjValidacion.msjJTextFieldRequeridos(dlgCancelarCompra);
//
//        } else {// fornulario valido
        procederCancelarCompra(compraTemp, dlgCancelarCompra.txtConcepto.getText());
//        }
    }
}
