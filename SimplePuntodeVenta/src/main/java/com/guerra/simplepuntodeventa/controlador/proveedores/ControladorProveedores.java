/*
 * Controlador para modulo de proveedores
 */
package com.guerra.simplepuntodeventa.controlador.proveedores;

import com.guerra.simplepuntodeventa.global.ConfiguracionEmpresa;
import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.CompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CuentaProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.HistorialAbonoProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.ProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Compra;
import com.guerra.simplepuntodeventa.modelo.entidades.CuentaProveedor;
import com.guerra.simplepuntodeventa.modelo.entidades.HistorialAbonoProveedor;
import com.guerra.simplepuntodeventa.modelo.entidades.Proveedor;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesProveedores;
import com.guerra.simplepuntodeventa.recursos.componentes.animacion.DlgProceso;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.FechaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.NumeroUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ServicioReporte;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJSpinner;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.utilerias.modelo.ParametroReporte;
import com.guerra.simplepuntodeventa.vista.clientes.DlgAbonarDeuda;
import com.guerra.simplepuntodeventa.vista.clientes.IfrmMenuClientes;
import com.guerra.simplepuntodeventa.vista.proveedores.IfrmMenuProveedores;
import com.guerra.simplepuntodeventa.vista.proveedores.PanEditarProveedor;
import com.guerra.simplepuntodeventa.vista.proveedores.PanEstadoCuentaProveedor;
import com.guerra.simplepuntodeventa.vista.proveedores.PanListProveedores;
import com.guerra.simplepuntodeventa.vista.proveedores.PanNuevoProveedor;
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
 * @author jaasiel
 */
public class ControladorProveedores {

    private final CompraDAOImpl compraDAO = DAOManager.getInstancia().getCompraDAO();
    private final CuentaProveedorDAOImpl cuentaProveedorDAO = DAOManager.getInstancia().getCuentaProveedorDAO();
    private final HistorialAbonoProveedorDAOImpl historialAbonoProverdorDAO = DAOManager.getInstancia().getHistorialAbonoProveedorDAO();

    private Proveedor proveedorEditar;
    private Compra compraAbonar;

    private final PanEditarProveedor panEditarProveedor;
    private final PanEstadoCuentaProveedor panCreditosProveedor;
    private final PanListProveedores panListProveedors;
    private final PanNuevoProveedor panNuevoProveedor;
    private final DlgAbonarDeuda dlgAbonarDeuda;

    private final ProveedorDAOImpl proveedorDAO = DAOManager.getInstancia().getProveedorDAO();

    public ControladorProveedores(PanEditarProveedor panEditarProveedor,
            PanEstadoCuentaProveedor panCreditosProveedor, PanListProveedores panListProveedors,
            PanNuevoProveedor panNuevoProveedor) {
        this.panEditarProveedor = panEditarProveedor;
        this.panCreditosProveedor = panCreditosProveedor;
        this.panListProveedors = panListProveedors;
        this.panNuevoProveedor = panNuevoProveedor;
        this.dlgAbonarDeuda = new DlgAbonarDeuda(null, true);
        init();
    }

    private void init() {
        panNuevoProveedor.btnGuardar.addActionListener((e) -> {
            guardarNuevoProveedor();
        });
        panListProveedors.btnEditar.addActionListener((e) -> {
            mostrarEditarProveedor();
        });
        panEditarProveedor.btnGuardar.addActionListener((e) -> {
            actualizarProveedor();
        });
        ComboBoxUtil.llenarComboEstado(panListProveedors.cbxEstado);
        panListProveedors.cbxEstado.addActionListener((e) -> {
            consultarProveedoresEstado();
        });
        panListProveedors.btnActivar.addActionListener((e) -> {
            activarProveedor();
        });
        panListProveedors.btnDesactivar.addActionListener((e) -> {
            desactivarProveedor();
        });
        panListProveedors.txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarProveedorNombre();
            }
        });
        panListProveedors.btnVerCuenta.addActionListener((e) -> {
            verEstadoCuentaProveedor();
        });
        panCreditosProveedor.btnAbonar.addActionListener((e) -> {
            mostrarDlgAbonarDeuda();
        });
        dlgAbonarDeuda.btnRealizarAbono.addActionListener((e) -> {
            realizarAbonoDeuda();
        });
        panCreditosProveedor.tblDeudas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                verMovimientosEHistorial();
            }
        });
        panCreditosProveedor.tblDeudas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int codigoTecla = e.getKeyCode();
                if (codigoTecla == 38 || codigoTecla == 40) {
                    verMovimientosEHistorial();
                }
            }
        });
        panCreditosProveedor.btnActualizar.addActionListener((ae) -> {
            verEstadoCuentaProveedor();
        });
    }

    private boolean imprimirComprobante(int idHistorialAbono) {
        boolean exito = true;
        try {

            String logo = ConfiguracionEmpresa.getInstancia().getLogo();

            ParametroReporte[] p = new ParametroReporte[]{
                new ParametroReporte("RUTA_LOGO", logo, ParametroReporte.OBJETO),
                new ParametroReporte("ID_HISTORIAL_ABONO", idHistorialAbono, ParametroReporte.OBJETO),
                new ParametroReporte("SUBRPT_DETALLE", "/reportes/sub_comprobante_abono_proveedor.jasper",
                ParametroReporte.NOMBRE_SUB_RPT)
            };

            exito = ServicioReporte.getInstancia().imprimir("/reportes/comprobante_abono_proveedor.jasper", p);

        } catch (HeadlessException | PrinterException | IOException | NullPointerException | JRException ex) {
            MsjException.msjErrorEstandar(dlgAbonarDeuda, "Error imprimiendo comprobante: " + ex.getMessage());
        }

        return exito;
    }

    private void resetFormularioEditarProveedor() {
        panEditarProveedor.txtNombre.setText("");
        panEditarProveedor.txtNIT.setText("");
        panEditarProveedor.txtDireccion.setText("");
        panEditarProveedor.txtTel.setText("");
        proveedorEditar = null;
    }

    private void llenarTablaProveedores(List<Proveedor> proveedores) {
        TablaUtil.llenarTablaConEntity(panListProveedors.tblProveedores, proveedores, new String[]{
            "nit", "nombre", "direccion", "telefono", "estado"
        }, 0);
    }

    private void consultarProveedoresEstado() {
        Estado e = (Estado) panListProveedors.cbxEstado.getSelectedItem();
        List<Proveedor> proveedores = FuncionesProveedores.consultarProveedoresEstado(e.getValor());
        llenarTablaProveedores(proveedores);
    }

    ////////metodos publicos///////////
    public void resetFormularioNuevoProveedor() {
        panNuevoProveedor.txtNombre.setText("");
        panNuevoProveedor.txtNIT.setText("");
        panNuevoProveedor.txtDireccion.setText("");
        panNuevoProveedor.txtTel.setText("");
    }

    public void consultarProveedores() {//listar proveedores
        panListProveedors.cbxEstado.setSelectedIndex(0);//con estado 1
    }

    //////////////////eventos/////////////////////////
    private void guardarNuevoProveedor() {

        //validar el formulario de proveedor
        if (ValidarJTextField.campoVacio(panNuevoProveedor.txtNombre)) {
            MsjValidacion.msjJTextFieldRequeridos(panNuevoProveedor);
        } else {

            //////nuevo cliente////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Proveedor p = new Proveedor();
            p.setNit(panNuevoProveedor.txtNIT.getText());
            p.setNombre(panNuevoProveedor.txtNombre.getText());
            p.setDireccion(panNuevoProveedor.txtDireccion.getText());
            p.setTelefono(panNuevoProveedor.txtTel.getText());
            p.setEstado(1);//activo
            p.setFechaCommit(new Date());
            p.setHoraCommit(new Date());
            p.setIdUsuario(user);

            try {
                proveedorDAO.create(p);
                resetFormularioNuevoProveedor();
                MsjInfo.msjRegistroExitoso(panNuevoProveedor);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(panNuevoProveedor, e.getMessage());
            }
        }
    }

    private void mostrarEditarProveedor() {
        proveedorEditar = TablaUtil.getEntityFilaSeleccionada(
                panListProveedors.tblProveedores, 0, Proveedor.class
        );
        if (proveedorEditar != null) {//validar que no sea null
            PanEditarProveedor pnl = panEditarProveedor;
            pnl.txtNombre.setText(proveedorEditar.getNombre());
            pnl.txtNIT.setText(proveedorEditar.getNit());
            pnl.txtDireccion.setText(proveedorEditar.getDireccion());
            pnl.txtTel.setText(proveedorEditar.getTelefono());
            PanelUtil.cambiarPanel(IfrmMenuProveedores.PANEL_CAMBIANTE, panEditarProveedor);
        }
    }

    private void actualizarProveedor() {
        ///////velidar el formulaio
        if (ValidarJTextField.campoVacio(panEditarProveedor.txtNombre)) {
            MsjValidacion.msjJTextFieldRequeridos(panEditarProveedor);
        } else {
            //////editar cliente////////
            proveedorEditar.setNit(panEditarProveedor.txtNIT.getText());
            proveedorEditar.setNombre(panEditarProveedor.txtNombre.getText());
            proveedorEditar.setDireccion(panEditarProveedor.txtDireccion.getText());
            proveedorEditar.setTelefono(panEditarProveedor.txtTel.getText());
            try {
                proveedorDAO.update(proveedorEditar);
                resetFormularioEditarProveedor();
                MsjInfo.msjActualizacionExitosa(panEditarProveedor);
                consultarProveedores();
                PanelUtil.cambiarPanel(IfrmMenuClientes.PANEL_CAMBIANTE, panListProveedors);
            } catch (Exception e) {
                MsjException.msjErrorActualizar(panEditarProveedor, e.getMessage());
            }
        }
    }

    private void activarProveedor() {
        proveedorEditar = TablaUtil.getEntityFilaSeleccionada(panListProveedors.tblProveedores, 0, Proveedor.class);
        if (proveedorEditar != null) {
            if (proveedorEditar.getEstado() == 1) {//si esta activado
                MsjInfo.msjRegistroYaActivado(panListProveedors);
            } else {//no activado
                proveedorEditar.setEstado(1);
                try {
                    proveedorDAO.update(proveedorEditar);
                    consultarProveedores();
                } catch (Exception e) {
                    MsjException.msjErrorActualizar(panListProveedors, e.getMessage());
                }
            }
        }
    }

    private void desactivarProveedor() {
        proveedorEditar = TablaUtil.getEntityFilaSeleccionada(panListProveedors.tblProveedores, 0, Proveedor.class);
        if (proveedorEditar != null) {
            if (proveedorEditar.getEstado() == 0) {//si ya esta desactivado
                MsjInfo.msjRegistroYaDesactivado(panListProveedors);
            } else {//activado
                //mensaje de confiramcion para desactivar
                if (MsjInfo.msjConfimacionDesactivar(panListProveedors) == JOptionPane.YES_OPTION) {
                    proveedorEditar.setEstado(0);
                    try {
                        proveedorDAO.update(proveedorEditar);
                        consultarProveedores();
                    } catch (Exception e) {
                        MsjException.msjErrorActualizar(panListProveedors, e.getMessage());
                    }
                }
            }
        }
    }

    private void buscarProveedorNombre() {
        Estado e = (Estado) panListProveedors.cbxEstado.getSelectedItem();
        List<Proveedor> proveedores = FuncionesProveedores.buscarProveedorNombreEstado(
                panListProveedors.txtBuscar.getText(), e.getValor()
        );
        llenarTablaProveedores(proveedores);
    }

    private void verEstadoCuentaProveedor() {
        Proveedor p = TablaUtil.getEntityFilaSeleccionada(panListProveedors.tblProveedores, 0, Proveedor.class);

        if (p != null) {

            TablaUtil.limpiarTabla(panCreditosProveedor.tblHistorial);
            TablaUtil.limpiarTabla(panCreditosProveedor.tblMovimientos);

            List<Compra> deudas = FuncionesProveedores.consultarDeudasAProveedor(p);

            TablaUtil.llenarTablaConEntity(panCreditosProveedor.tblDeudas, deudas, new String[]{
                "idCompra", "fecha", "total", "saldo"
            }, 0);

            EntityManager em = proveedorDAO.getEntityManager();
            List<Double> resultList = em.createQuery("SELECT FUNCTION('deuda_proveedor', :proveedor) FROM Proveedor p", Double.class)
                    .setParameter("proveedor", p.getIdProveedor())
                    .getResultList();
            panCreditosProveedor.lblNombreCliente.setText(p.getNombre());
            panCreditosProveedor.lblDeuda.setText(NumeroUtil.formatearAMoneda(resultList.get(0)));

            PanelUtil.cambiarPanel(IfrmMenuProveedores.PANEL_CAMBIANTE, panCreditosProveedor);

        } else {
            MsjInfo.msjSeleccioneFila(panListProveedors);
        }
    }

    private void mostrarDlgAbonarDeuda() {

        compraAbonar = TablaUtil.getEntityFilaSeleccionada(panCreditosProveedor.tblDeudas, 0, Compra.class);

        if (compraAbonar != null) {

            if (compraAbonar.getSaldo() > 0) {

                ValidarJSpinner.resetValuesDouble(dlgAbonarDeuda.spnAbono, 0);
                dlgAbonarDeuda.txtFecha.setDate(new Date());
                dlgAbonarDeuda.lblMaximo.setText(
                        "MÁXIMO: " + NumeroUtil.formatearAMoneda(compraAbonar.getSaldo())
                );
                dlgAbonarDeuda.cbxImprimir.setSelected(false);
                dlgAbonarDeuda.setLocationRelativeTo(panCreditosProveedor);
                dlgAbonarDeuda.setVisible(true);
            } else {
                MsjInfo.msjNoSaldoPendiente(panCreditosProveedor);
            }
        } else {
            MsjInfo.msjSeleccioneFila(panCreditosProveedor);
        }
    }

    private void realizarAbonoDeuda() {
        if (dlgAbonarDeuda.txtFecha.getDate() == null
                || !ValidarJSpinner.valorDoubleMinimo(0, dlgAbonarDeuda.spnAbono)
                || ValidarJTextField.campoVacio(dlgAbonarDeuda.txtComentario)) {

            MsjValidacion.msjJTextFieldRequeridos(dlgAbonarDeuda);

        } else if (!ValidarJSpinner.valorDoubleRango(0, compraAbonar.getSaldo(), dlgAbonarDeuda.spnAbono)) {

            MsjValidacion.msjAbonoDeudaInvalido(dlgAbonarDeuda, compraAbonar.getSaldo());
        } else {

            SwingWorker<String, String> tarea = new SwingWorker<String, String>() {
                @Override
                protected String doInBackground() throws Exception {
                    publish("");

                    try {

                        Date fecha = dlgAbonarDeuda.txtFecha.getDate();
                        double abono = (double) dlgAbonarDeuda.spnAbono.getValue();
                        String comentario = dlgAbonarDeuda.txtComentario.getText();
                        double saldoPendiente = compraAbonar.getSaldo();
                        String documento = dlgAbonarDeuda.txtDoc.getText();

                        //1. registrar el movimiento en la cuenta del proveedor
                        Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
                        CuentaProveedor cp = new CuentaProveedor();
                        cp.setFecha(FechaUtil.formatearFechaTextoSqlite3(fecha));
                        cp.setComentario(comentario);
                        cp.setCargo(0.00D);
                        cp.setAbono(abono);
                        cp.setSaldo(saldoPendiente - abono);
                        cp.setIdCompra(compraAbonar);
                        cp.setFechaCommit(new Date());
                        cp.setHoraCommit(new Date());
                        cp.setIdUsuario(user);
                        cuentaProveedorDAO.create(cp);

                        //2. registrar historial
                        HistorialAbonoProveedor h = new HistorialAbonoProveedor();
                        h.setIdCompra(compraAbonar);
                        h.setFecha(FechaUtil.formatearFechaTextoSqlite3(fecha));
                        h.setAbono(abono);
                        h.setDocumento(documento);
                        h.setHoraCommit(new Date());
                        h.setFechaCommit(new Date());
                        h.setIdUsuario(user);
                        historialAbonoProverdorDAO.create(h);

                        //3. actualizar el saldo
                        compraAbonar.getCuentaProveedorList().add(cp);
                        compraAbonar.getHistorialAbonoProveedorList().add(h);
                        compraAbonar.setSaldo(saldoPendiente - abono);
                        compraDAO.update(compraAbonar);

                        if (dlgAbonarDeuda.cbxImprimir.isSelected()) {
                            if (!imprimirComprobante(h.getIdHistorialAbonoProveedor())) {

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
                            verEstadoCuentaProveedor();
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

        Compra c = TablaUtil.getEntityFilaSeleccionada(
                panCreditosProveedor.tblDeudas, 0, Compra.class
        );

        if (c != null) {

            TablaUtil.llenarTablaSinEntity(
                    panCreditosProveedor.tblMovimientos,
                    c.getCuentaProveedorList(),
                    new String[]{
                        "fecha", "comentario", "cargo", "abono", "saldo"
                    }
            );

            TablaUtil.llenarTablaSinEntity(
                    panCreditosProveedor.tblHistorial,
                    c.getHistorialAbonoProveedorList(),
                    new String[]{
                        "fecha", "abono", "documento"
                    }
            );

        }

    }
}
