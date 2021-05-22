/*
 * Controlador para modulo de proveedores
 */
package com.guerra.simplepuntodeventa.controlador.proveedores;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.CompraDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.CuentaProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.HistorialAbonoProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.dao.ProveedorDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Cliente;
import com.guerra.simplepuntodeventa.modelo.entidades.Proveedor;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesClientes;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesProveedores;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.vista.clientes.DlgAbonarDeuda;
import com.guerra.simplepuntodeventa.vista.clientes.IfrmMenuClientes;
import com.guerra.simplepuntodeventa.vista.proveedores.IfrmMenuProveedores;
import com.guerra.simplepuntodeventa.vista.proveedores.PanEditarProveedor;
import com.guerra.simplepuntodeventa.vista.proveedores.PanEstadoCuentaProveedor;
import com.guerra.simplepuntodeventa.vista.proveedores.PanListProveedores;
import com.guerra.simplepuntodeventa.vista.proveedores.PanNuevoProveedor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author jaasiel
 */
public class ControladorProveedores {

    private final CompraDAOImpl compraDAO = DAOManager.getInstancia().getCompraDAO();
    private final CuentaProveedorDAOImpl cuentaProveedorDAO = DAOManager.getInstancia().getCuentaProveedorDAO();
    private final HistorialAbonoProveedorDAOImpl historialAbonoProverdorDAO = DAOManager.getInstancia().getHistorialAbonoProveedorDAO();

    private Proveedor proveedorEditar;

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
}
