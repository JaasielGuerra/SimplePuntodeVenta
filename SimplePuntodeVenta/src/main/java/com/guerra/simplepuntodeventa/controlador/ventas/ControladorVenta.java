/*
 * Implementada la logica para el modulo de ventas, aqui se realizan las ventas,
 * se ven las ventas realizadas, se cancelan, etc.
 */
package com.guerra.simplepuntodeventa.controlador.ventas;

import com.guerra.simplepuntodeventa.controlador.busquedaarticulo.ControladorBusquedaArticulo;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.Tipo;
import com.guerra.simplepuntodeventa.modelo.dao.ArticuloDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CuentaClienteDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.DetalleVentaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.VentaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;
import com.guerra.simplepuntodeventa.modelo.entidades.Cliente;
import com.guerra.simplepuntodeventa.modelo.entidades.DetalleVenta;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.modelo.entidades.Venta;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesClientes;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesInventario;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesVentas;
import com.guerra.simplepuntodeventa.global.ConfiguracionEmpresa;
import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.entidades.Servicio;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesServicios;
import com.guerra.simplepuntodeventa.recursos.componentes.animacion.DlgProceso;
import com.guerra.simplepuntodeventa.recursos.componentes.modelos.ModeloJTextFieldCodigoBarra;
import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.FechaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.NumeroUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.recursos.utilerias.ServicioReporte;
import com.guerra.simplepuntodeventa.recursos.utilerias.modelo.ParametroReporte;
import com.guerra.simplepuntodeventa.vista.ventas.DlgCancelarVenta;
import com.guerra.simplepuntodeventa.vista.ventas.DlgCobrar;
import com.guerra.simplepuntodeventa.vista.ventas.DlgVentasRealizadas;
import com.guerra.simplepuntodeventa.vista.ventas.IfrmVentas;
import com.guerra.simplepuntodeventa.vista.ventas.PanCredito;
import com.guerra.simplepuntodeventa.vista.ventas.PanDetalleVenta;
import com.guerra.simplepuntodeventa.vista.ventas.PanEfectivo;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Jaasiel
 */
public class ControladorVenta {

    //constantes para determinar origen de venta al momento de registrarla
    private static final short NUEVA = 0;
    private static final short PENDIENTE = 1;

    //constantes para el tipo de objeto que se vende en el detalle
    private static final short DETALLE_ARTICULO = 1;
    private static final short DETALLE_SERVICIO = 2;

    //para salvar temporalmente una venta para cencelarla
    private Venta ventaTemp;

    private final VentaDAOImpl ventaDAO = DAOManager.getInstancia().getVentaDAO();
    private final ArticuloDAOImpl articuloDAO = DAOManager.getInstancia().getArticuloDAO();
    private final DetalleVentaDAOImpl detalleVentaDAO = DAOManager.getInstancia().getDetalleVentaDAO();
    private final CuentaClienteDAOImpl cuentaClienteDAO = DAOManager.getInstancia().getMovimientoClienteDAO();

    //para determinar el tipo de venta
    private int tipoVenta;
    private final int VENTA_CONTADO = 1;
    private final int VENTA_CREDITO = 2;

    //para llevar un conteo global de los tab en la venta
    private long correlativoTabVenta;

    private final IfrmVentas ifrmVentas;
    private final DlgVentasRealizadas dlgVentasRealizadas;
    private final DlgCancelarVenta dlgCancelarVenta;

    private final DlgCobrar dlgCobrar;
    private final PanEfectivo panEfectivo;
    private final PanCredito panCredito;

    private ControladorBusquedaArticulo controladorBusquedaArticulo;

    public ControladorVenta(IfrmVentas ifrmMenuVentas) {
        this.ifrmVentas = ifrmMenuVentas;
        this.dlgVentasRealizadas = new DlgVentasRealizadas(null, true);
        this.dlgCobrar = new DlgCobrar(null, true);
        this.panEfectivo = new PanEfectivo();
        this.panCredito = new PanCredito();
        this.dlgCancelarVenta = new DlgCancelarVenta(null, true);
        init();
    }

    private void init() {
        ifrmVentas.btnVentasRealizadas.addActionListener((e) -> {
            verVentasRealizadas();
        });
        ifrmVentas.btnCobrar.addActionListener((e) -> {
            ventanaCobrar();
        });
        ifrmVentas.ftdTotal.setValue(0.00D);
        correlativoTabVenta = ifrmVentas.tbdDetalle.getTabCount();
        cargarVentasPendientes();
        ifrmVentas.tbdDetalle.add("VENTA-" + (++correlativoTabVenta), new PanDetalleVenta());
        ifrmVentas.tbdDetalle.setSelectedIndex(ifrmVentas.tbdDetalle.getTabCount() - 1);
        ifrmVentas.spnCantidad.setValue(1);
        ifrmVentas.txtCodigo.addKeyListener(new ModeloJTextFieldCodigoBarra());
        ifrmVentas.txtCodigo.addActionListener((e) -> {
            agregarArticuloDetalle();
        });
        //----------------------spinner cantidad de articulo--------------------
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) ifrmVentas.spnCantidad.getEditor();
        JFormattedTextField fTextfield = editor.getTextField();
        fTextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        fTextfield.commitEdit();
                        agregarArticuloDetalle();
                    } catch (ParseException ex) {
                        MsjException.msjErrorEstandar(ifrmVentas,
                                "Ingrese un número mayor a cero (" + ex.getMessage() + ")");
                    }
                }
            }

        });
        fTextfield.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    JFormattedTextField f = (JFormattedTextField) e.getSource();
                    f.selectAll();
                });
            }
        });
        //----------------------------------------------------------------------
        ifrmVentas.btnAceptar.addActionListener((e) -> {
            agregarArticuloDetalle();
        });
        ifrmVentas.btnPendiente.addActionListener((e) -> {
            ponerVentaPendiente();
        });
        ifrmVentas.btnEliminarVenta.addActionListener((e) -> {
            eliminarTabVenta();
        });
        ifrmVentas.btnQuitarArticulo.addActionListener((e) -> {
            quitarArticuloTabla();
        });
        ifrmVentas.btnBuscarArticulo.addActionListener((e) -> {
            mostrarVentabaBuscarArticulo();
        });
        ifrmVentas.btnAumentarCantidad.addActionListener((e) -> {
            aumentarCantidadArticulo();
        });
        ifrmVentas.btnDisminuirCantidad.addActionListener((e) -> {
            disminuirCantidadArticulo();
        });
        //---------------------------input de cantidad--------------------------
        JSpinner spn = new JSpinner();
        spn.setModel(new SpinnerNumberModel(1, 1, null, 1));
        JSpinner.DefaultEditor editorSpn = (JSpinner.DefaultEditor) spn.getEditor();
        JFormattedTextField textField = editorSpn.getTextField();
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    JFormattedTextField tf = (JFormattedTextField) e.getSource();
                    tf.selectAll();
                });

            }

        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        textField.commitEdit();
                    } catch (ParseException ex) {
                        MsjException.msjErrorEstandar(ifrmVentas,
                                "Ingrese un número mayor a cero (" + ex.getMessage() + ")");
                    }
                }
            }
        });
        String[] options = new String[]{"Aceptar", "Cancelar"};
        controladorBusquedaArticulo = new ControladorBusquedaArticulo(ifrmVentas, (seleccionado) -> {

            spn.setValue(1);//resetear valor

            int opcion = JOptionPane.showOptionDialog(ifrmVentas, spn, "¿Cantidad a agregar?",
                    JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (opcion == JOptionPane.OK_OPTION) {

                int cantidad = (int) spn.getValue();
                ifrmVentas.spnCantidad.setValue(cantidad);

                setArticuloServicioATabla(seleccionado, null, DETALLE_ARTICULO);
                resetCodigoBarra();
            }
        });
        //----------------------------------------------------------------------
        ifrmVentas.tbdDetalle.addChangeListener((e) -> {
            calcularCantidadArticulosYTotalTabSeleccionado();
        });
        panEfectivo.ftdEfectivo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    JFormattedTextField t = (JFormattedTextField) e.getSource();
                    t.selectAll();
                });
            }
        });
        panEfectivo.ftdEfectivo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                calcularCambio();
            }

            // para no permitir espacios en el ingreso del efectivo
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (caracter == ' ') {
                    e.consume();
                }
            }
        });
        dlgCobrar.btnEfectivo.addActionListener((e) -> {
            cobrarConEfectivo();
        });
        dlgCobrar.btnCredito.addActionListener((e) -> {
            cobrarConCredito();
        });
        dlgCobrar.btnCerrarVentana.addActionListener((e) -> {
            dlgCobrar.dispose();
        });
        dlgCobrar.btnCobrarRegistrar.addActionListener((e) -> {
            cobrarYRegistrarVenta();
        });
        dlgVentasRealizadas.btnCerrar.addActionListener((e) -> {
            dlgVentasRealizadas.dispose();
        });
        dlgVentasRealizadas.tblVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                verDetalleVenta();
            }
        });
        dlgVentasRealizadas.tblVentas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int codigoTecla = e.getKeyCode();
                if (codigoTecla == 38 || codigoTecla == 40) {
                    verDetalleVenta();
                }
            }

        });
        ComboBoxUtil.llenarComboEstadoCompraVenta(dlgVentasRealizadas.cmbEstado);
        ComboBoxUtil.llenarComboTipoCompraVenta(dlgVentasRealizadas.cmbTipo);
        dlgVentasRealizadas.fechaMin.setDate(new Date());
        dlgVentasRealizadas.fechaMax.setDate(new Date());
        dlgVentasRealizadas.btnIr.addActionListener((e) -> {
            filtrarVentasRangofecha();
        });
        dlgVentasRealizadas.cmbEstado.addItemListener((e) -> {
            filtrarVentaEstado(e);
        });
        dlgVentasRealizadas.cmbTipo.addItemListener((e) -> {
            filtrarVentasTipo(e);
        });
        dlgVentasRealizadas.btnImprimir.addActionListener((e) -> {
            reimprimirTicket();
        });
        dlgVentasRealizadas.btnCancelarVenta.addActionListener((e) -> {
            cancelarVenta();
        });
        dlgCancelarVenta.btnConfirmar.addActionListener((e) -> {
            confirmarCancelarVenta();
        });
    }

    // cancela una venta y producir una devolucion al inventario
    private void procederCancelarVenta(Venta v, String motivo) {
        SwingWorker<String, String> tarea = new SwingWorker<String, String>() {
            @Override
            protected String doInBackground() throws Exception {
                publish("");

                try {

                    // 1. Cambiar estado de venta -> 0 cancelada
                    v.setEstado(0);
                    ventaDAO.update(v);

                    //2. ahora por cada detalle se debe cambiar estado
                    for (DetalleVenta d : v.getDetalleVentaList()) {

                        d.setEstado(0);//cancelado
                        detalleVentaDAO.update(d);

                    }

                } catch (Exception e) {
                    return e.getMessage();
                }

                return "true";
            }

            @Override
            protected void done() {

                try {
                    boolean todoBien = "true".equals(get());

                    SwingUtilities.invokeLater(() -> {
                        DlgProceso.ocultarDlgProceso();
                        // si es true todo ok
                        if (todoBien) {
                            resetFormularioCancelarVenta();
                            MsjInfo.msjExitoEstandar(dlgCancelarVenta);
                            dlgCancelarVenta.dispose();
                            verVentasRealizadas();
                        }
                    });

                    if (!todoBien) {
                        MsjException.msjErrorEstandar(dlgCancelarVenta, this.get());
                    }

                } catch (InterruptedException | ExecutionException e) {
                    MsjException.msjErrorEstandar(dlgCancelarVenta, e.getMessage());
                }

            }

            @Override
            protected void process(List<String> chunks) {
                SwingUtilities.invokeLater(() -> {
                    DlgProceso.mostrarDlgProceso(dlgCancelarVenta);
                });
            }

        };
        tarea.execute();
    }

    //reiniciar la ventana de cancelar venta
    private void resetFormularioCancelarVenta() {
        ValidarJTextField.resetValues(new JTextField[]{
            dlgCancelarVenta.txtConcepto
        });
    }

    //reiniciar los filtros del dialogo de ventas realizadas
    private void resetFiltros() {
        dlgVentasRealizadas.cmbEstado.setEnabled(false);
        dlgVentasRealizadas.cmbTipo.setEnabled(false);
        dlgVentasRealizadas.cmbEstado.setSelectedIndex(0);
        dlgVentasRealizadas.cmbTipo.setSelectedIndex(0);
        dlgVentasRealizadas.fechaMin.setDate(new Date());
        dlgVentasRealizadas.fechaMax.setDate(new Date());
        dlgVentasRealizadas.cmbEstado.setEnabled(true);
        dlgVentasRealizadas.cmbTipo.setEnabled(true);
    }

    //calcula el total de la venta del tab seleccionado
    // y tambien el numero de articulos
    private void calcularCantidadArticulosYTotalTabSeleccionado() {
        if (ifrmVentas.tbdDetalle.getTabCount() > 0) {
            DefaultTableModel m = (DefaultTableModel) getTablaTabSeleccionado().getModel();
            int count = m.getRowCount();
            double total = 0.00D;
            int cantArticulos = 0;

            for (int i = 0; i < count; i++) {
                total += (double) m.getValueAt(i, 6);
                cantArticulos += (int) m.getValueAt(i, 4);
            }
            ifrmVentas.ftdTotal.setValue(NumeroUtil.redondear(total, 2));
            ifrmVentas.lblCantidadArticulos.setText(cantArticulos + "");
        }
    }

    //valida si el detalle existe, y actualiza sus datos, en caso contrario agrega nuevo
    private void agregarDetalleATabla(JTable tablaDetalle, DetalleVenta d) {

        DefaultTableModel m = (DefaultTableModel) tablaDetalle.getModel();
        boolean coincidencia = false;

        // si esta vacio se agrega fila
        if (m.getRowCount() == 0) {

            agregarFilaDetalleATablaNuevaCompra(m, d);

        } else {//sino se busca una coincidencia
            for (int i = 0; i < m.getRowCount(); i++) {

                // obtener detalle de la tabla
                DetalleVenta dtActual = (DetalleVenta) m.getValueAt(i, 0);

                Integer idObjetoTabla = dtActual.getTipo() == DETALLE_ARTICULO
                        ? dtActual.getIdArticulo().getIdArticulo()
                        : dtActual.getIdServicio().getIdServicio();

                Integer idObjetoInsertar = d.getTipo() == DETALLE_ARTICULO
                        ? d.getIdArticulo().getIdArticulo()
                        : d.getIdServicio().getIdServicio();

                // validar coincidencia con el id del articulo/servicio
                if (idObjetoTabla.equals(idObjetoInsertar)) {

                    coincidencia = true;

                    // actualizar detalle en la tabla
                    int nuevaCantidad = dtActual.getCantidad() + d.getCantidad();
                    double nuevoSubtotal = NumeroUtil.redondear(d.getPrecioUnitario() * nuevaCantidad, 2);

                    //actualizar los datos
                    Double gananciaDetalle = d.getTipo() == DETALLE_ARTICULO ? d.getIdArticulo().getGanancia() : d.getPrecioUnitario();
                    d.setCantidad(nuevaCantidad);
                    d.setSubTotal(nuevoSubtotal);
                    d.setGanancia(nuevaCantidad * gananciaDetalle);

                    m.setValueAt(d, i, 0);
                    m.setValueAt(d.getPrecioUnitario(), i, 3);
                    m.setValueAt(d.getCantidad(), i, 4);
                    m.setValueAt(d.getGanancia(), i, 5);
                    m.setValueAt(d.getSubTotal(), i, 6);
                    break;
                }

            }

            if (!coincidencia) {//si no hay coincidencias, pues agregar fila nueva
                agregarFilaDetalleATablaNuevaCompra(m, d);
            }
        }

    }

    //agregar una fila nueva con un detalle a la tabla de detalle de venta
    private void agregarFilaDetalleATablaNuevaCompra(DefaultTableModel m, DetalleVenta d) {

        String codigo = d.getTipo() == DETALLE_ARTICULO ? d.getIdArticulo().getCod1() : d.getIdServicio().getCodigo();
        String descripcion = d.getTipo() == DETALLE_ARTICULO ? d.getIdArticulo().getDescripcion() : d.getIdServicio().getDescripcion();

        Object[] f = new Object[7];
        f[0] = d;
        f[1] = codigo;
        f[2] = descripcion;
        f[3] = d.getPrecioUnitario();
        f[4] = d.getCantidad();
        f[5] = d.getGanancia();
        f[6] = d.getSubTotal();

        m.addRow(f);
    }

    //reiniciar los componentes de la barra para codigo
    private void resetCodigoBarra() {
        ifrmVentas.spnCantidad.setValue(1);
        ifrmVentas.txtCodigo.setText("");
        ifrmVentas.txtCodigo.requestFocusInWindow();

    }

    //devuelve la tabla del tab que este seleccionado
    private JTable getTablaTabSeleccionado() {
        return ((PanDetalleVenta) ifrmVentas.tbdDetalle.getSelectedComponent()).tblDetalleVenta;
    }

    //devuelve la tabla del tab que se indique en un indice
    private JTable getTablaEnTab(int indice) {
        return ((PanDetalleVenta) ifrmVentas.tbdDetalle.getComponentAt(indice)).tblDetalleVenta;
    }

    //devuelve el panel de detalle del tab que este seleccionado
    private PanDetalleVenta getPanDetalleTabSeleccionado() {
        return (PanDetalleVenta) ifrmVentas.tbdDetalle.getSelectedComponent();
    }

    //devuelve el panel de detalle del tab que se indique
    private PanDetalleVenta getPanDetalleEnTab(int i) {
        return (PanDetalleVenta) ifrmVentas.tbdDetalle.getComponentAt(i);
    }

    // meter un articulo/servicio como detalle a la tabla
    private void setArticuloServicioATabla(Articulo a, Servicio s, int tipoDetalle) {

        int cantidad = (int) ifrmVentas.spnCantidad.getValue();

        Double precioUnitario = 0.00D;
        Double gananciaDetalle = 0.00D;

        if (tipoDetalle == DETALLE_SERVICIO) {

            String[] options = new String[]{"Aceptar", "Cancelar"};
            JComboBox<Double> precio = new JComboBox<>();

            precio.addItem(s.getPrecioA());
            precio.addItem(s.getPrecioB());
            precio.addItem(s.getPrecioC());

            int opcion = JOptionPane.showOptionDialog(ifrmVentas, precio, "¿Precio?",
                    JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (opcion != JOptionPane.OK_OPTION) {
                return;
            }

            precioUnitario = (Double) precio.getSelectedItem();
            gananciaDetalle = precioUnitario;
        }

        if (tipoDetalle == DETALLE_ARTICULO) {
            precioUnitario = a.getPrecioVenta();
            gananciaDetalle = a.getGanancia();
        }

        ///////nuevo detalle de venta/////////
        DetalleVenta d = new DetalleVenta();

        d.setCantidad(cantidad);
        d.setPrecioUnitario(precioUnitario);
        d.setSubTotal(cantidad * precioUnitario);
        d.setGanancia(cantidad * gananciaDetalle);
        d.setTipo(tipoDetalle);
        d.setEstado(1);
        d.setIdArticulo(a);
        d.setIdServicio(s);
        d.setIdUsuario((Usuario) Session.getInstancia().getAttribute("user"));

        agregarDetalleATabla(getTablaTabSeleccionado(), d);
        calcularCantidadArticulosYTotalTabSeleccionado();
    }

    //imprimir un ticket de cobro
    private boolean imprimirTicket(Integer idVenta) {

        boolean exito = true;

        try {
            String logo = ConfiguracionEmpresa.getInstancia().getLogo();

            exito = ServicioReporte.getInstancia().imprimir("/reportes/ticket_cobro.jasper", new ParametroReporte[]{
                new ParametroReporte("LOGO", logo, ParametroReporte.OBJETO),
                new ParametroReporte("ID_VENTA", idVenta, ParametroReporte.OBJETO),
                new ParametroReporte("SUBRPT_1", "/reportes/subrpt_ticket.jasper", ParametroReporte.NOMBRE_SUB_RPT),
                new ParametroReporte("SUBRPT_2", "/reportes/subrpt_ticket_detalle.jasper", ParametroReporte.NOMBRE_SUB_RPT)
            });

        } catch (HeadlessException | PrinterException | IOException | NullPointerException | JRException e) {
            MsjException.msjErrorEstandar(dlgCobrar, e.getMessage());
        }
        return exito;
    }

    //registrar una venta en la base de datos
    private void registrarVenta(Venta venta, List<DetalleVenta> detalle, String concepto, short origenVenta) {

        SwingWorker<String, String> tarea = new SwingWorker<String, String>() {
            @Override
            protected String doInBackground() throws Exception {
                publish("");

                try {

                    // 1. registrar venta
                    ventaDAO.create(venta);

                    // recorrer la lista para registrar cada detalle de venta
                    for (DetalleVenta d : detalle) {

                        // 2. registrar detalle
                        d.setIdVenta(venta);
                        detalleVentaDAO.create(d);

                    }

                    if (dlgCobrar.cbxImprimirTicket.isSelected()) {
                        imprimirTicket(venta.getIdVenta());
                    }

                } catch (Exception e) {
                    return e.getMessage();
                }

                return "true";
            }

            @Override
            protected void done() {
                try {

                    boolean todoBien = "true".equals(get());
                    SwingUtilities.invokeLater(() -> {
                        DlgProceso.ocultarDlgProceso();
                        // si termina todo ok
                        if (todoBien) {
                            dlgCobrar.dispose();
                            resetVistaVenta();
                            MsjInfo.msjRegistroExitoso(dlgCobrar);
                        }
                    });
                    if (!todoBien) {
                        MsjException.msjErrorGuardar(dlgCobrar, this.get());
                    }

                } catch (InterruptedException | ExecutionException ex) {
                    MsjException.msjErrorEstandar(dlgCobrar, ex.getMessage());
                }

            }

            @Override
            protected void process(List<String> chunks) {
                SwingUtilities.invokeLater(() -> {
                    DlgProceso.mostrarDlgProceso(dlgCobrar);
                });
            }

        };
        tarea.execute();

    }

    //eliminar el tab seleccionado del panel con la tabla de detalle de venta
    private void borrarTabSeleccionadoPanelTablaVenta() {

        //borrar filas de la tabla antes de eliminar tab
        DefaultTableModel m = (DefaultTableModel) getTablaTabSeleccionado().getModel();
        m.setRowCount(0);

        int index = ifrmVentas.tbdDetalle.getSelectedIndex();
        int countTab = ifrmVentas.tbdDetalle.getTabCount();

        ifrmVentas.tbdDetalle.remove(index);

        // si solo habia uno pues despues de borrar se agrega un tab nuevo
        // para que no se quede sin tabla para agregar articulo
        if (countTab == 1) {
            ifrmVentas.tbdDetalle.add("VENTA-" + correlativoTabVenta, new PanDetalleVenta());
            ifrmVentas.tbdDetalle.setSelectedIndex(countTab - 1);
        }
    }

    //este metodo llama a otros para reiniciar por completo la vista de la venta.
    // es utilizado despues de hacer una venta exitosa
    private void resetVistaVenta() {
        correlativoTabVenta++;//aumentar el correlativo de venta
        borrarTabSeleccionadoPanelTablaVenta();
        calcularCantidadArticulosYTotalTabSeleccionado();
        resetCodigoBarra();
    }

    //sirve para llenar la tabla de ventas realizadas
    private void llenarTablaVentasRealizadas(List<Venta> ventas) {
        DefaultTableModel m = (DefaultTableModel) dlgVentasRealizadas.tblDetalle.getModel();
        m.setRowCount(0);//limpiar tabla de detalle de venta 
        TablaUtil.llenarTablaConEntity(dlgVentasRealizadas.tblVentas, ventas, new String[]{
            "idVenta", "fecha", "tipoVenta", "idCliente.nombreCompleto", "totalGanancia",
            "total", "estado"
        }, 0);
    }

    //devuelve la lista de los detalles de venta de la tabla
    private List<DetalleVenta> getListDetalleVentaTabSeleccionado() {
        List<DetalleVenta> detalle = new ArrayList<>();
        DefaultTableModel m = (DefaultTableModel) getTablaTabSeleccionado().getModel();
        int nFilas = m.getRowCount();
        for (int i = 0; i < nFilas; i++) {
            DetalleVenta dt = (DetalleVenta) m.getValueAt(i, 0);
            detalle.add(dt);
        }
        return detalle;
    }

    //obtener una lista de detalle de la tabla del tab indicado
    private List<DetalleVenta> getListDetalleVentaEnTab(int indice) {
        List<DetalleVenta> detalle = new ArrayList<>();
        DefaultTableModel m = (DefaultTableModel) getTablaEnTab(indice).getModel();
        int nFilas = m.getRowCount();
        for (int i = 0; i < nFilas; i++) {
            DetalleVenta dt = (DetalleVenta) m.getValueAt(i, 0);
            detalle.add(dt);
        }
        return detalle;
    }

    /**
     * Cargar las ventas que se guardaron como pendientes
     */
    private void cargarVentasPendientes() {

        //consultar las pendientes
        List<Venta> pendientes = ventaDAO.readByNameQuery("Venta.findByEstado", "estado", 2);

        //recorrerlas
        for (int i = 0; i < pendientes.size(); i++) {

            PanDetalleVenta p = new PanDetalleVenta();

            //inidicar y salvar temporalmente la venta guardada
            p.setVentaGuardadaBD(true);
            p.setVenta(pendientes.get(i));
            p.setDetalleVenta(pendientes.get(i).getDetalleVentaList());

            //agregar los detalles a cada tabla
            for (DetalleVenta d : pendientes.get(i).getDetalleVentaList()) {
                agregarDetalleATabla(p.tblDetalleVenta, d);
            }

            ifrmVentas.tbdDetalle.add(p);
            ifrmVentas.tbdDetalle.setTitleAt(i, pendientes.get(i).getNombre());
        }

    }

    /**
     * Llena la venta y el detalle para luego registrarla en la base de datos
     *
     * @param totalVenta
     * @param tipoVenta
     * @param concepto
     * @param cliente
     * @param saldo
     */
    private void llenarRegistrarVenta(Double totalVenta, int tipoVenta,
            String concepto, Cliente cliente, Double saldo) {

        Venta venta = new Venta();

        //obtener el detalle de la tabla
        List<DetalleVenta> detalle = getListDetalleVentaTabSeleccionado();

        ///////nueva venta///////////
        venta.setFecha(FechaUtil.formatearFechaTextoSqlite3(new Date()));
        venta.setTotal(totalVenta);
        venta.setTotalGanancia(detalle.stream().mapToDouble(d -> d.getGanancia()).sum());
        venta.setTipoVenta(tipoVenta);
        venta.setIdCliente(cliente);
        venta.setIdUsuario((Usuario) Session.getInstancia().getAttribute("user"));
        venta.setEstado(1);//realizado
        venta.setSaldo(saldo);
        venta.setNombre(null);
        venta.setFechaCommit(new Date());
        venta.setHoraCommit(new Date());

        registrarVenta(venta, detalle, concepto, NUEVA);
    }

    //////////////////////////////EVENTOS/////////////////////////////////////////
    private void verVentasRealizadas() {

        resetFiltros();

        SwingWorker<List<Venta>, String> tarea = new SwingWorker<List<Venta>, String>() {
            @Override
            protected List<Venta> doInBackground() throws Exception {

                publish("");
                List<Venta> ventas = FuncionesVentas.obtenerVentasFecha(new Date());
                return ventas;
            }

            @Override
            protected void done() {
                try {
                    List<Venta> ventas = get();
                    SwingUtilities.invokeLater(() -> {
                        llenarTablaVentasRealizadas(ventas);
                        DlgProceso.ocultarDlgProceso();
                        dlgVentasRealizadas.setLocationRelativeTo(ifrmVentas);
                        dlgVentasRealizadas.setVisible(true);
                    });

                } catch (InterruptedException | ExecutionException ex) {
                    MsjException.msjErrorEstandar(ifrmVentas, ex.getMessage());
                }
            }

            @Override
            protected void process(List<String> chunks) {
                SwingUtilities.invokeLater(() -> {
                    DlgProceso.mostrarDlgProceso(ifrmVentas);
                });
            }

        };
        tarea.execute();

    }

    private void ventanaCobrar() {
        if (getTablaTabSeleccionado().getRowCount() > 0) {

            dlgCobrar.ftdTotal.setValue(ifrmVentas.ftdTotal.getValue());
            dlgCobrar.cbxImprimirTicket.setSelected(false);
            dlgCobrar.setLocationRelativeTo(ifrmVentas);
            cobrarConEfectivo();
            dlgCobrar.setVisible(true);

        } else {
            MsjValidacion.msjTablaVaciaSinDetalles(ifrmVentas);
        }
    }

    private void agregarArticuloDetalle() {

        if (ifrmVentas.txtCodigo.getText().isEmpty()) {
            MsjInfo.msjNoArticuloBuscado(ifrmVentas);
        } else {

            Articulo a = null;
            Servicio s = null;

            String c = ifrmVentas.txtCodigo.getText();

            int tipo = ifrmVentas.cmbTipo.getSelectedIndex() + 1;

            switch (tipo) {
                case DETALLE_ARTICULO:
                    a = FuncionesInventario.getArticuloPorCodigo(c);
                    break;
                case DETALLE_SERVICIO:
                    s = FuncionesServicios.getServicioPorCodigo(c);
                    break;
                default:
                    break;
            }

            if (a != null || s != null) {

                setArticuloServicioATabla(a, s, tipo);
                resetCodigoBarra();

            } else {
                resetCodigoBarra();
                MsjInfo.msjNoResultadoCodigo(ifrmVentas, c);
            }
        }
    }

    private void ponerVentaPendiente() {

        int index = ifrmVentas.tbdDetalle.getSelectedIndex();
        String nombreActual = ifrmVentas.tbdDetalle.getTitleAt(index);

        String nombre = JOptionPane.showInputDialog(ifrmVentas, "NOMBRE DE VENTA:", nombreActual);

        if (nombre != null) {// si es null es porque el usuario le dio cancelar al dialogo
            if (!nombre.isEmpty()) {
                ifrmVentas.tbdDetalle.setTitleAt(index, nombre.toUpperCase());
            }
            int indexSeleccionado = ifrmVentas.tbdDetalle.getTabCount();
            ifrmVentas.tbdDetalle.add("VENTA-" + (++correlativoTabVenta), new PanDetalleVenta());
            ifrmVentas.tbdDetalle.setSelectedIndex(indexSeleccionado);
            calcularCantidadArticulosYTotalTabSeleccionado();
        }
    }

    private void eliminarTabVenta() {
        if (getTablaTabSeleccionado().getRowCount() == 0
                || MsjInfo.msjConfimacionEliminarTabVenta(ifrmVentas) == JOptionPane.YES_OPTION) {

            borrarTabSeleccionadoPanelTablaVenta();
            calcularCantidadArticulosYTotalTabSeleccionado();
        }
    }

    private void quitarArticuloTabla() {

        int filaSeleccionada = getTablaTabSeleccionado().getSelectedRow();
        if (filaSeleccionada > -1) {
            if (MsjInfo.msjConfimacionEliminarArticuloTablaVenta(ifrmVentas) == JOptionPane.YES_OPTION) {
                DefaultTableModel m = (DefaultTableModel) getTablaTabSeleccionado().getModel();
                m.removeRow(filaSeleccionada);

                int countFilas = getTablaTabSeleccionado().getRowCount();
                if (countFilas == 0) {
                    eliminarTabVenta();
                } else {
                    calcularCantidadArticulosYTotalTabSeleccionado();
                }
            }
        } else {
            MsjInfo.msjSeleccioneFila(ifrmVentas);
        }
    }

    private void mostrarVentabaBuscarArticulo() {
        controladorBusquedaArticulo.mostrarDialogoBuscarArticulo();
    }

    private void aumentarCantidadArticulo() {
        DetalleVenta d = TablaUtil.getEntityFilaSeleccionada(getTablaTabSeleccionado(), 0, DetalleVenta.class);
        if (d != null) {

            DefaultTableModel m = (DefaultTableModel) getTablaTabSeleccionado().getModel();

            //actualizar el detalle
            Double gananciaDetalle = d.getTipo() == DETALLE_ARTICULO ? d.getIdArticulo().getGanancia() : d.getPrecioUnitario();
            d.setCantidad(d.getCantidad() + 1);
            d.setSubTotal(d.getCantidad() * d.getPrecioUnitario());
            d.setGanancia(d.getCantidad() * gananciaDetalle);

            //actualizar la tabla
            m.setValueAt(d.getCantidad(), getTablaTabSeleccionado().getSelectedRow(), 4);
            m.setValueAt(d.getGanancia(), getTablaTabSeleccionado().getSelectedRow(), 5);
            m.setValueAt(d.getSubTotal(), getTablaTabSeleccionado().getSelectedRow(), 6);

            calcularCantidadArticulosYTotalTabSeleccionado();

        } else {
            MsjInfo.msjSeleccioneFila(ifrmVentas);
        }
    }

    private void disminuirCantidadArticulo() {
        DetalleVenta d = TablaUtil.getEntityFilaSeleccionada(getTablaTabSeleccionado(), 0, DetalleVenta.class);
        if (d != null) {

            // disminuir siempre y cuando no este por debajo o igual a 1
            if (d.getCantidad() > 1) {

                DefaultTableModel m = (DefaultTableModel) getTablaTabSeleccionado().getModel();

                //actualizar el detalle
                Double gananciaDetalle = d.getTipo() == DETALLE_ARTICULO ? d.getIdArticulo().getGanancia() : d.getPrecioUnitario();
                d.setCantidad(d.getCantidad() - 1);
                d.setSubTotal(d.getCantidad() * d.getPrecioUnitario());
                d.setGanancia(d.getCantidad() * gananciaDetalle);

                //actualizar la tabla
                m.setValueAt(d.getCantidad(), getTablaTabSeleccionado().getSelectedRow(), 4);
                m.setValueAt(d.getGanancia(), getTablaTabSeleccionado().getSelectedRow(), 5);
                m.setValueAt(d.getSubTotal(), getTablaTabSeleccionado().getSelectedRow(), 6);

                calcularCantidadArticulosYTotalTabSeleccionado();
            }
        } else {
            MsjInfo.msjSeleccioneFila(ifrmVentas);
        }
    }

    private void cobrarConEfectivo() {
        panEfectivo.ftdEfectivo.setValue(0.00D);
        panEfectivo.ftdCambio.setValue(0.00D);
        panEfectivo.ftdEfectivo.setBackground(UIManager.getColor("JFormattedTextField.background"));
        panEfectivo.ftdCambio.setBackground(UIManager.getColor("JFormattedTextField.background"));
        tipoVenta = VENTA_CONTADO;
        dlgCobrar.btnEfectivo.setEnabled(false);
        dlgCobrar.btnCredito.setEnabled(true);
        PanelUtil.cambiarPanel(dlgCobrar.panCobro, panEfectivo);
        panEfectivo.ftdEfectivo.requestFocusInWindow();
    }

    private void cobrarConCredito() {
        tipoVenta = VENTA_CREDITO;
        dlgCobrar.btnCredito.setEnabled(false);
        dlgCobrar.btnEfectivo.setEnabled(true);
        List<Cliente> clientes = FuncionesClientes.consultarClientesEstado(1);
        panCredito.cmbCliente.removeAllItems();
        clientes.forEach((c) -> {
            panCredito.cmbCliente.addItem(c);
        });
        PanelUtil.cambiarPanel(dlgCobrar.panCobro, panCredito);
        panCredito.cmbCliente.requestFocusInWindow();
    }

    private void calcularCambio() {
        if (!panEfectivo.ftdEfectivo.getText().isEmpty()) {

            try {

                panEfectivo.ftdEfectivo.commitEdit();
                dlgCobrar.ftdTotal.commitEdit();

                double efectivo = new Double(panEfectivo.ftdEfectivo.getValue().toString());
                double totalVenta = new Double(dlgCobrar.ftdTotal.getValue().toString());
                double cambio = NumeroUtil.redondear(efectivo - totalVenta, 2);
                panEfectivo.ftdCambio.setValue(cambio);

                if (totalVenta > efectivo) {
                    panEfectivo.ftdEfectivo.setBackground(Color.pink);
                    panEfectivo.ftdCambio.setBackground(Color.yellow);
                    return;
                }

                panEfectivo.ftdEfectivo.setBackground(UIManager.getColor("JFormattedTextField.background"));
                panEfectivo.ftdCambio.setBackground(UIManager.getColor("JFormattedTextField.background"));

            } catch (ParseException ex) {
                panEfectivo.ftdEfectivo.setBackground(Color.pink);
            }
        } else {
            panEfectivo.ftdEfectivo.setBackground(Color.pink);
        }
    }

    private void cobrarYRegistrarVenta() {

        double totalVenta = new Double(dlgCobrar.ftdTotal.getValue().toString());
        double efectivo = new Double(panEfectivo.ftdEfectivo.getValue().toString());

        switch (tipoVenta) {
            case VENTA_CONTADO: {// venta al contado

                //validar el efectivo
                if (totalVenta > efectivo) {
                    MsjValidacion.msjEfectivoInvalido(dlgCobrar, totalVenta);
                    return;
                }

                llenarRegistrarVenta(totalVenta, VENTA_CONTADO, "Venta al contado",
                        (Cliente) Session.getInstancia().getAttribute("cliente"),
                        null);

            }
            break;
            case VENTA_CREDITO: {

                Cliente cliente = (Cliente) panCredito.cmbCliente.getSelectedItem();

                Double creditoDisponible = FuncionesClientes.creditoDisponible(cliente);

                if (totalVenta > creditoDisponible) {
                    MsjValidacion.msjCreditoInsuficiente(dlgCobrar, creditoDisponible);
                    return;
                }

                llenarRegistrarVenta(totalVenta, VENTA_CREDITO, "Venta al crédito",
                        cliente, totalVenta);
            }
            break;
            default:
                break;
        }
    }

    private void verDetalleVenta() {
        Venta v = TablaUtil.getEntityFilaSeleccionada(
                dlgVentasRealizadas.tblVentas, 0, Venta.class
        );

        if (v != null) {
            DefaultTableModel m = (DefaultTableModel) dlgVentasRealizadas.tblDetalle.getModel();
            m.setRowCount(0);

            List<DetalleVenta> detalleVentaList = FuncionesVentas.obtenerDetalleVenta(v);

            detalleVentaList.stream().map((d) -> {

                String codigo = d.getTipo() == DETALLE_ARTICULO ? d.getIdArticulo().getCod1() : d.getIdServicio().getCodigo();
                String descripcion = d.getTipo() == DETALLE_ARTICULO ? d.getIdArticulo().getDescripcion() : d.getIdServicio().getDescripcion();

                Object[] rowData = new Object[6];
                rowData[0] = codigo;
                rowData[1] = descripcion;
                rowData[2] = d.getPrecioUnitario();
                rowData[3] = d.getCantidad();
                rowData[4] = d.getGanancia();
                rowData[5] = d.getSubTotal();
                return rowData;
            }).forEachOrdered((rowData) -> {
                m.addRow(rowData);
            });
        }

    }

    private void filtrarVentasRangofecha() {
        SwingWorker<List<Venta>, String> tarea = new SwingWorker<List<Venta>, String>() {
            @Override
            protected List<Venta> doInBackground() throws Exception {
                publish("");
                List<Venta> ventas = FuncionesVentas.obtenerVentasRangoFecha(
                        dlgVentasRealizadas.fechaMin.getDate(), dlgVentasRealizadas.fechaMax.getDate()
                );

                return ventas;
            }

            @Override
            protected void done() {
                try {
                    List<Venta> ventas = get();
                    SwingUtilities.invokeLater(() -> {
                        llenarTablaVentasRealizadas(ventas);
                        DlgProceso.ocultarDlgProceso();
                    });
                } catch (InterruptedException | ExecutionException ex) {
                    MsjException.msjErrorEstandar(dlgVentasRealizadas, ex.getMessage());
                }
            }

            @Override
            protected void process(List<String> chunks) {
                SwingUtilities.invokeLater(() -> {
                    DlgProceso.mostrarDlgProceso(dlgVentasRealizadas);
                });
            }

        };
        tarea.execute();
    }

    private void filtrarVentaEstado(ItemEvent event) {
        if (dlgVentasRealizadas.cmbEstado.isEnabled() && event.getStateChange() == ItemEvent.SELECTED) {

            SwingWorker<List<Venta>, String> tarea = new SwingWorker<List<Venta>, String>() {
                @Override
                protected List<Venta> doInBackground() throws Exception {
                    publish("");
                    Estado e = (Estado) event.getItem();
                    List<Venta> ventas = FuncionesVentas.obtenerVentasEstado(e.getValor());
                    return ventas;
                }

                @Override
                protected void done() {
                    try {
                        List<Venta> ventas = get();
                        SwingUtilities.invokeLater(() -> {
                            llenarTablaVentasRealizadas(ventas);
                            DlgProceso.ocultarDlgProceso();
                        });
                    } catch (InterruptedException | ExecutionException ex) {
                        MsjException.msjErrorEstandar(dlgVentasRealizadas, ex.getMessage());
                    }
                }

                @Override
                protected void process(List<String> chunks) {
                    SwingUtilities.invokeLater(() -> {
                        DlgProceso.mostrarDlgProceso(dlgVentasRealizadas);
                    });
                }

            };
            tarea.execute();

        }
    }

    private void filtrarVentasTipo(ItemEvent e) {
        if (dlgVentasRealizadas.cmbTipo.isEnabled() && e.getStateChange() == ItemEvent.SELECTED) {

            SwingWorker<List<Venta>, String> tarea = new SwingWorker<List<Venta>, String>() {
                @Override
                protected List<Venta> doInBackground() throws Exception {

                    publish("");
                    List<Venta> ventas = FuncionesVentas.obtenerVentasTipo(((Tipo) e.getItem()).getValor());
                    return ventas;
                }

                @Override
                protected void done() {
                    try {
                        List<Venta> ventas = get();
                        SwingUtilities.invokeLater(() -> {
                            llenarTablaVentasRealizadas(ventas);
                            DlgProceso.ocultarDlgProceso();
                        });
                    } catch (InterruptedException | ExecutionException ex) {
                        MsjException.msjErrorEstandar(dlgVentasRealizadas, ex.getMessage());
                    }
                }

                @Override
                protected void process(List<String> chunks) {
                    SwingUtilities.invokeLater(() -> {
                        DlgProceso.mostrarDlgProceso(dlgVentasRealizadas);
                    });
                }

            };
            tarea.execute();
        }
    }

    private void reimprimirTicket() {
        Venta v = TablaUtil.getEntityFilaSeleccionada(
                dlgVentasRealizadas.tblVentas, 0, Venta.class
        );

        if (v != null) {

            SwingWorker<Boolean, String> tarea = new SwingWorker<Boolean, String>() {
                @Override
                protected Boolean doInBackground() throws Exception {

                    publish("");

                    return imprimirTicket(v.getIdVenta());
                }

                @Override
                protected void done() {
                    try {
                        boolean exito = get();
                        SwingUtilities.invokeLater(() -> {
                            DlgProceso.ocultarDlgProceso();
                            if (exito) {
                                MsjInfo.msjExitoEstandar(dlgVentasRealizadas);
                            }
                        });
                    } catch (InterruptedException | ExecutionException ex) {
                        MsjException.msjErrorEstandar(dlgVentasRealizadas, ex.getMessage());
                    }
                }

                @Override
                protected void process(List<String> chunks) {
                    SwingUtilities.invokeLater(() -> {
                        DlgProceso.mostrarDlgProceso(dlgVentasRealizadas);
                    });
                }

            };
            tarea.execute();

        } else {
            MsjInfo.msjSeleccioneFila(dlgVentasRealizadas);
        }

    }

    private void cancelarVenta() {
        ventaTemp = TablaUtil.getEntityFilaSeleccionada(
                dlgVentasRealizadas.tblVentas, 0, Venta.class
        );

        if (ventaTemp != null) {

            if (ventaTemp.getEstado() == 0)//si ya esta cancelada
            {
                MsjInfo.msjVentaYaCancelada(dlgVentasRealizadas);
            } else {

                ValidarJTextField.resetValues(
                        new JTextField[]{
                            dlgCancelarVenta.txtConcepto
                        }
                );
                dlgCancelarVenta.setLocationRelativeTo(dlgVentasRealizadas);
                dlgCancelarVenta.setVisible(true);
            }

        } else {
            MsjInfo.msjSeleccioneFila(dlgVentasRealizadas);
        }
    }

    private void confirmarCancelarVenta() {
//        if (ValidarJTextField.campoVacio(dlgCancelarVenta.txtConcepto)) {
//        MsjValidacion.msjJTextFieldRequeridos(dlgCancelarVenta);
//        } else {
        procederCancelarVenta(ventaTemp, dlgCancelarVenta.txtConcepto.getText());
//        }
    }

}
