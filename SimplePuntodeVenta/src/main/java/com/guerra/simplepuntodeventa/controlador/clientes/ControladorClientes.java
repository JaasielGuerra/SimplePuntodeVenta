/*
 * Controlador para los clientes
 */
package com.guerra.simplepuntodeventa.controlador.clientes;

import com.guerra.simplepuntodeventa.global.ConfiguracionEmpresa;
import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.ClienteDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CuentaClienteDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.HistorialAbonoClienteDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.VentaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Cliente;
import com.guerra.simplepuntodeventa.modelo.entidades.CuentaCliente;
import com.guerra.simplepuntodeventa.modelo.entidades.HistorialAbonoCliente;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.modelo.entidades.Venta;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesClientes;
import com.guerra.simplepuntodeventa.recursos.componentes.animacion.DlgProceso;
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
import com.guerra.simplepuntodeventa.recursos.utilerias.ServicioReporte;
import com.guerra.simplepuntodeventa.recursos.utilerias.modelo.ParametroReporte;
import com.guerra.simplepuntodeventa.vista.clientes.DlgAbonarDeuda;
import com.guerra.simplepuntodeventa.vista.clientes.IfrmMenuClientes;
import com.guerra.simplepuntodeventa.vista.clientes.PanEditarCliente;
import com.guerra.simplepuntodeventa.vista.clientes.PanEstadoCuentaCliente;
import com.guerra.simplepuntodeventa.vista.clientes.PanListClientes;
import com.guerra.simplepuntodeventa.vista.clientes.PanNuevoCliente;
import com.guerra.simplepuntodeventa.vista.clientes.VistaClientesBean;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Jaasiel
 */
public class ControladorClientes {

    private final VentaDAOImpl ventaDAO = DAOManager.getInstancia().getVentaDAO();
    private final CuentaClienteDAOImpl cuentaClienteDAO = DAOManager.getInstancia().getMovimientoClienteDAO();
    private final HistorialAbonoClienteDAOImpl historialAbonoClienteDAO = DAOManager.getInstancia().getHistorialAbonoClienteDAO();

    private Cliente clienteEditar;
    private Venta ventaAbonar;

    private final PanEditarCliente panEditarCliente;
    private final PanEstadoCuentaCliente panCreditosCliente;
    private final PanListClientes panListClientes;
    private final PanNuevoCliente panNuevoCliente;
    private final DlgAbonarDeuda dlgAbonarDeuda;

    private final ClienteDAOImpl clienteDAO = DAOManager.getInstancia().getClienteDAO();

    public ControladorClientes(VistaClientesBean bean) {
        this.panEditarCliente = bean.getPanEditarCliente();
        this.panCreditosCliente = bean.getPanEstadoCuenta();
        this.panListClientes = bean.getPanListClientes();
        this.panNuevoCliente = bean.getPanNuevoCliente();
        this.dlgAbonarDeuda = new DlgAbonarDeuda(null, true);
        init();
    }

    private void init() {
        panNuevoCliente.btnGuardar.addActionListener((e) -> {
            guardarNuevoCliente();
        });
        panListClientes.btnEditar.addActionListener((e) -> {
            mostrarEditarCliente();
        });
        panEditarCliente.btnGuardar.addActionListener((e) -> {
            actualizarCliente();
        });
        ComboBoxUtil.llenarComboEstado(panListClientes.cbxEstado);
        panListClientes.cbxEstado.addActionListener((e) -> {
            consultarClientesEstado();
        });
        panListClientes.txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarClienteNombre();
            }
        });
        panListClientes.btnActivar.addActionListener((e) -> {
            activarCliente();
        });
        panListClientes.btnDesactivar.addActionListener((e) -> {
            desactivarCliente();
        });
        panListClientes.btnVerCuenta.addActionListener((e) -> {
            verEstadoCuentaCliente();
        });
        panCreditosCliente.btnAbonar.addActionListener((e) -> {
            mostrarDlgAbonarDeuda();
        });
        dlgAbonarDeuda.btnRealizarAbono.addActionListener((e) -> {
            realizarAbonoDeuda();
        });
        panCreditosCliente.tblDeudas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                verMovimientosEHistorial();
            }

        });
        panCreditosCliente.tblDeudas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int codigoTecla = e.getKeyCode();
                if (codigoTecla == 38 || codigoTecla == 40) {
                    verMovimientosEHistorial();
                }
            }
        });
        panCreditosCliente.btnActualizar.addActionListener((ae) -> {
            verEstadoCuentaCliente();
        });
    }

    private boolean imprimirComprobante(int idHistorialAbono) {
        boolean exito = true;
        try {

            String logo = ConfiguracionEmpresa.getInstancia().getLogo();

            ParametroReporte[] p = new ParametroReporte[]{
                new ParametroReporte("RUTA_LOGO", logo, ParametroReporte.OBJETO),
                new ParametroReporte("ID_HISTORIAL_ABONO", idHistorialAbono, ParametroReporte.OBJETO),
                new ParametroReporte("SUBRPT_DETALLE", "/reportes/sub_comprobante_abono_cliente.jasper",
                ParametroReporte.NOMBRE_SUB_RPT)
            };

            exito = ServicioReporte.getInstancia().imprimir("/reportes/comprobante_abono_cliente.jasper", p);

        } catch (HeadlessException | PrinterException | IOException | NullPointerException | JRException ex) {
            MsjException.msjErrorEstandar(dlgAbonarDeuda, "Error imprimiendo comprobante: " + ex.getMessage());
        }

        return exito;
    }

    private void resetFormularioEditarCliente() {
        panEditarCliente.txtNombre.setText("");
        panEditarCliente.txtDPI.setText("");
        panEditarCliente.txtDireccion.setText("");
        panEditarCliente.txtTel.setText("");
        panEditarCliente.spnLimite.setValue(0.00D);
        clienteEditar = null;
    }

    private void llenarTablaClientes(List<Cliente> clientes) {
        TablaUtil.llenarTablaConEntity(panListClientes.tblClientes, clientes, new String[]{
            "nombreCompleto", "dpi", "direccion", "telefono", "limite", "estado"
        }, 0);
    }

    ////////metodos publicos///////////
    public void resetFormularioNuevoCliente() {
        panNuevoCliente.txtNombre.setText("");
        panNuevoCliente.txtDPI.setText("");
        panNuevoCliente.txtDireccion.setText("");
        panNuevoCliente.txtTel.setText("");
        panNuevoCliente.spnLimite.setValue(0.00D);
    }

    public void consultarClientes() {//listar clientes
        panListClientes.cbxEstado.setSelectedIndex(0);//con estado 1
    }

    //////////////////eventos/////////////////////////
    private void guardarNuevoCliente() {

        ///////velidar el formulaio
        if (ValidarJTextField.campoVacio(panNuevoCliente.txtNombre)) {
            MsjValidacion.msjJTextFieldRequeridos(panNuevoCliente);
        } else {

            //////nuevo cliente////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Cliente c = new Cliente();
            c.setDpi(panNuevoCliente.txtDPI.getText());
            c.setNombreCompleto(panNuevoCliente.txtNombre.getText());
            c.setDireccion(panNuevoCliente.txtDireccion.getText());
            c.setTelefono(panNuevoCliente.txtTel.getText());
            c.setLimite((double) panNuevoCliente.spnLimite.getValue());
            c.setEstado(1);//activo
            c.setFechaCommit(new Date());
            c.setHoraCommit(new Date());
            c.setIdUsuario(user);

            try {
                clienteDAO.create(c);
                resetFormularioNuevoCliente();
                MsjInfo.msjRegistroExitoso(panNuevoCliente);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(panNuevoCliente, e.getMessage());
            }
        }

    }

    private void mostrarEditarCliente() {
        clienteEditar = TablaUtil.getEntityFilaSeleccionada(
                panListClientes.tblClientes, 0, Cliente.class
        );
        if (clienteEditar != null) {//validar que no sea null
            PanEditarCliente pnl = panEditarCliente;
            pnl.txtNombre.setText(clienteEditar.getNombreCompleto());
            pnl.txtDPI.setText(clienteEditar.getDpi());
            pnl.txtDireccion.setText(clienteEditar.getDireccion());
            pnl.txtTel.setText(clienteEditar.getTelefono());
            pnl.spnLimite.setValue(clienteEditar.getLimite());
            PanelUtil.cambiarPanel(IfrmMenuClientes.PANEL_CAMBIANTE, panEditarCliente);
        }
    }

    private void actualizarCliente() {
        ///////velidar el formulaio
        if (ValidarJTextField.campoVacio(panEditarCliente.txtNombre)) {
            MsjValidacion.msjJTextFieldRequeridos(panEditarCliente);
        } else {
            //////editar cliente////////
            clienteEditar.setDpi(panEditarCliente.txtDPI.getText());
            clienteEditar.setNombreCompleto(panEditarCliente.txtNombre.getText());
            clienteEditar.setDireccion(panEditarCliente.txtDireccion.getText());
            clienteEditar.setTelefono(panEditarCliente.txtTel.getText());
            clienteEditar.setLimite((double) panEditarCliente.spnLimite.getValue());
            try {
                clienteDAO.update(clienteEditar);
                resetFormularioEditarCliente();
                MsjInfo.msjActualizacionExitosa(panEditarCliente);
                consultarClientes();
                PanelUtil.cambiarPanel(IfrmMenuClientes.PANEL_CAMBIANTE, panListClientes);
            } catch (Exception e) {
                MsjException.msjErrorActualizar(panEditarCliente, e.getMessage());
            }
        }
    }

    private void consultarClientesEstado() {
        Estado e = (Estado) panListClientes.cbxEstado.getSelectedItem();
        List<Cliente> clientes = FuncionesClientes.consultarClientesEstado(e.getValor());
        llenarTablaClientes(clientes);
    }

    private void buscarClienteNombre() {
        Estado e = (Estado) panListClientes.cbxEstado.getSelectedItem();
        List<Cliente> clientes = FuncionesClientes.buscarClienteNombreEstado(
                panListClientes.txtBuscar.getText(), e.getValor()
        );
        llenarTablaClientes(clientes);
    }

    private void activarCliente() {
        clienteEditar = TablaUtil.getEntityFilaSeleccionada(panListClientes.tblClientes, 0, Cliente.class);
        if (clienteEditar != null) {
            if (clienteEditar.getEstado() == 1) {//si esta activado
                MsjInfo.msjRegistroYaActivado(panListClientes);
            } else {//no activado
                clienteEditar.setEstado(1);
                try {
                    clienteDAO.update(clienteEditar);
                    consultarClientes();
                } catch (Exception e) {
                    MsjException.msjErrorActualizar(panListClientes, e.getMessage());
                }
            }
        }
    }

    private void desactivarCliente() {
        clienteEditar = TablaUtil.getEntityFilaSeleccionada(panListClientes.tblClientes, 0, Cliente.class);
        if (clienteEditar != null) {
            if (clienteEditar.getEstado() == 0) {//si ya esta desactivado
                MsjInfo.msjRegistroYaDesactivado(panListClientes);
            } else {//activado
                //mensaje de confiramcion para desactivar
                if (MsjInfo.msjConfimacionDesactivar(panListClientes) == JOptionPane.YES_OPTION) {
                    clienteEditar.setEstado(0);
                    try {
                        clienteDAO.update(clienteEditar);
                        consultarClientes();
                    } catch (Exception e) {
                        MsjException.msjErrorActualizar(panListClientes, e.getMessage());
                    }
                }
            }
        }
    }

    private void verEstadoCuentaCliente() {

        Cliente c = TablaUtil.getEntityFilaSeleccionada(panListClientes.tblClientes, 0, Cliente.class);

        if (c != null) {

            TablaUtil.limpiarTabla(panCreditosCliente.tblHistorial);
            TablaUtil.limpiarTabla(panCreditosCliente.tblMovimientos);

            List<Venta> deudas = FuncionesClientes.consultarDeudasDeCliente(c);
                    
            TablaUtil.llenarTablaConEntity(panCreditosCliente.tblDeudas, deudas, new String[]{
                "idVenta", "fecha", "total", "saldo"
            }, 0);

            EntityManager em = clienteDAO.getEntityManager();
            List<Double> resultList = em.createQuery("SELECT FUNCTION('deuda_cliente', :cliente) FROM Cliente c", Double.class)
                    .setParameter("cliente", c.getIdCliente())
                    .getResultList();
            panCreditosCliente.lblNombreCliente.setText(c.getNombreCompleto());
            panCreditosCliente.lblDeuda.setText(NumeroUtil.formatearAMoneda(resultList.get(0)));

            PanelUtil.cambiarPanel(IfrmMenuClientes.PANEL_CAMBIANTE, panCreditosCliente);

        } else {
            MsjInfo.msjSeleccioneFila(panListClientes);
        }
    }

    private void mostrarDlgAbonarDeuda() {

        ventaAbonar = TablaUtil.getEntityFilaSeleccionada(panCreditosCliente.tblDeudas, 0, Venta.class);

        if (ventaAbonar != null) {

            if (ventaAbonar.getSaldo() > 0) {

                ValidarJSpinner.resetValuesDouble(dlgAbonarDeuda.spnAbono, 0);
                dlgAbonarDeuda.txtFecha.setDate(new Date());
                dlgAbonarDeuda.lblMaximo.setText(
                        "MÁXIMO: " + NumeroUtil.formatearAMoneda(ventaAbonar.getSaldo())
                );
                dlgAbonarDeuda.cbxImprimir.setSelected(false);
                dlgAbonarDeuda.setLocationRelativeTo(panCreditosCliente);
                dlgAbonarDeuda.setVisible(true);
            } else {
                MsjInfo.msjNoSaldoPendiente(panCreditosCliente);
            }
        } else {
            MsjInfo.msjSeleccioneFila(panCreditosCliente);
        }
    }

    private void realizarAbonoDeuda() {
        if (dlgAbonarDeuda.txtFecha.getDate() == null
                || !ValidarJSpinner.valorDoubleMinimo(0, dlgAbonarDeuda.spnAbono)
                || ValidarJTextField.campoVacio(dlgAbonarDeuda.txtComentario)) {

            MsjValidacion.msjJTextFieldRequeridos(dlgAbonarDeuda);

        } else if (!ValidarJSpinner.valorDoubleRango(0, ventaAbonar.getSaldo(), dlgAbonarDeuda.spnAbono)) {

            MsjValidacion.msjAbonoDeudaInvalido(dlgAbonarDeuda, ventaAbonar.getSaldo());
        } else {

            SwingWorker<String, String> tarea = new SwingWorker<String, String>() {
                @Override
                protected String doInBackground() throws Exception {
                    publish("");

                    try {

                        Date fecha = dlgAbonarDeuda.txtFecha.getDate();
                        double abono = (double) dlgAbonarDeuda.spnAbono.getValue();
                        String comentario = dlgAbonarDeuda.txtComentario.getText();
                        double saldoPendiente = ventaAbonar.getSaldo();
                        String documento = dlgAbonarDeuda.txtDoc.getText();

                        //1. registrar el movimiento en la cuenta del cliente
                        Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
                        CuentaCliente cc = new CuentaCliente();
                        cc.setFecha(FechaUtil.formatearFechaTextoSqlite3(fecha));
                        cc.setComentario(comentario);
                        cc.setCargo(0.00D);
                        cc.setAbono(abono);
                        cc.setSaldo(saldoPendiente - abono);
                        cc.setIdVenta(ventaAbonar);
                        cc.setFechaCommit(new Date());
                        cc.setHoraCommit(new Date());
                        cc.setIdUsuario(user);
                        cuentaClienteDAO.create(cc);

                        //2. registrar historial
                        HistorialAbonoCliente h = new HistorialAbonoCliente();
                        h.setIdVenta(ventaAbonar);
                        h.setFecha(FechaUtil.formatearFechaTextoSqlite3(fecha));
                        h.setAbono(abono);
                        h.setDocumento(documento);
                        h.setHoraCommit(new Date());
                        h.setFechaCommit(new Date());
                        h.setIdUsuario(user);
                        historialAbonoClienteDAO.create(h);

                        //3. actualizar el saldo
                        ventaAbonar.getCuentaClienteList().add(cc);
                        ventaAbonar.getHistorialAbonoClienteList().add(h);
                        ventaAbonar.setSaldo(saldoPendiente - abono);
                        ventaDAO.update(ventaAbonar);

                        if (dlgAbonarDeuda.cbxImprimir.isSelected()) {
                            if (!imprimirComprobante(h.getIdHistorialAbonoCliente())) {

                                return "error en generación de comprobante";
                            }

                        }

                    } catch (Exception e) {
                        return e.getMessage();
                    }

                    return "true";
                }

                @Override
                protected void done() {
                    try {

                        SwingUtilities.invokeLater(() -> {
                            DlgProceso.ocultarDlgProceso();
                        });
                        String valorRetornado = get();
                        boolean todoBien = "true".equals(valorRetornado);
                        if (todoBien) {
                            verEstadoCuentaCliente();
                            MsjInfo.msjExitoEstandar(dlgAbonarDeuda);
                            dlgAbonarDeuda.dispose();
                        } else {
                            MsjException.msjErrorEstandar(dlgAbonarDeuda, "Proceso finalizó con error: " + valorRetornado);
                        }

                    } catch (InterruptedException | ExecutionException ex) {
                        MsjException.msjErrorEstandar(dlgAbonarDeuda, "Error al obtener devolución del proceso: " + ex.getMessage());
                    }

                }

                @Override
                protected void process(List<String> chunks) {
                    SwingUtilities.invokeLater(() -> {
                        DlgProceso.mostrarDlgProceso(dlgAbonarDeuda);
                    });
                }

            };
            tarea.execute();
        }

    }

    private void verMovimientosEHistorial() {
        Venta v = TablaUtil.getEntityFilaSeleccionada(
                panCreditosCliente.tblDeudas, 0, Venta.class
        );

        if (v != null) {

            TablaUtil.llenarTablaSinEntity(
                    panCreditosCliente.tblMovimientos,
                    v.getCuentaClienteList(),
                    new String[]{
                        "fecha", "comentario", "cargo", "abono", "saldo"
                    }
            );

            TablaUtil.llenarTablaSinEntity(
                    panCreditosCliente.tblHistorial,
                    v.getHistorialAbonoClienteList(),
                    new String[]{
                        "fecha", "abono", "documento"
                    }
            );

        }
    }

}
