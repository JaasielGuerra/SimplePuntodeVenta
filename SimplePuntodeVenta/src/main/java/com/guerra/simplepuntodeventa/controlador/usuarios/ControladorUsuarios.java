/*
 * Controlador para el modulo de usuarios
 */
package com.guerra.simplepuntodeventa.controlador.usuarios;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.UsuarioDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesUsuarios;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.vista.usuarios.IfrmMenuUsuarios;
import com.guerra.simplepuntodeventa.vista.usuarios.PanCrearUsuario;
import com.guerra.simplepuntodeventa.vista.usuarios.PanEditarUsuario;
import com.guerra.simplepuntodeventa.vista.usuarios.PanListUsuarios;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author jaasiel
 */
public class ControladorUsuarios {

    private Usuario usuarioEditar;

    private final PanCrearUsuario panCrearUsuario;
    private final PanEditarUsuario panEditarUsuario;
    private final PanListUsuarios panListUsuarios;

    private final UsuarioDAOImpl usuarioDAO = DAOManager.getInstancia().getUsuarioDAO();

    public ControladorUsuarios(PanCrearUsuario panCrearUsuario, PanEditarUsuario panEditarUsuario,
            PanListUsuarios panListUsuarios) {
        this.panCrearUsuario = panCrearUsuario;
        this.panEditarUsuario = panEditarUsuario;
        this.panListUsuarios = panListUsuarios;
        initComponents();
    }

    private void initComponents() {
        panCrearUsuario.btnGuardar.addActionListener((e) -> {
            guardarNuevoUsuario();
        });
        panListUsuarios.btnEditar.addActionListener((e) -> {
            mostrarEditarUsuario();
        });
        panEditarUsuario.btnGuardar.addActionListener((e) -> {
            actualizarUsuario();
        });
        ComboBoxUtil.llenarComboEstado(panListUsuarios.cbxEstado);
        panListUsuarios.cbxEstado.addActionListener((e) -> {
            consultarUsuariosEstado();
        });
        panListUsuarios.btnActivar.addActionListener((e) -> {
            activarUsuario();
        });
        panListUsuarios.btnDesactivar.addActionListener((e) -> {
            desactivarUsuario();
        });
    }

    private void llenarTablaUsuarios(List<Usuario> users) {
        TablaUtil.llenarTablaConEntity(panListUsuarios.tblUsuarios, users, new String[]{
            "nombre", "user", "password", "estado"
        }, 0);
    }

    private void consultarUsuariosEstado() {
        Estado e = (Estado) panListUsuarios.cbxEstado.getSelectedItem();
        List<Usuario> users = FuncionesUsuarios.consultarUsuariosEstado(e.getValor());
        llenarTablaUsuarios(users);
    }

    private void resetFormularioEditarUsuario() {
        panEditarUsuario.txtNombre.setText("");
        panEditarUsuario.txtUser.setText("");
        panEditarUsuario.txtPass.setText("");
    }

    //////////metodos publicos/////////////
    public void resetFormularioNuevoUsuario() {
        panCrearUsuario.txtNombre.setText("");
        panCrearUsuario.txtUser.setText("");
        panCrearUsuario.txtPass.setText("");
    }

    public void consultarUsuarios() {//listar usuarios
        panListUsuarios.cbxEstado.setSelectedIndex(0);//con estado 1
    }

    ///////////////eventos///////////////
    private void guardarNuevoUsuario() {
        //validar el formulario de proveedor
        if (ValidarJTextField.camposVacios(panCrearUsuario.txtNombre,
                panCrearUsuario.txtPass, panCrearUsuario.txtUser)) {
            MsjValidacion.msjJTextFieldRequeridos(panCrearUsuario);
        } else {

            /////nuevo usuario
            Usuario user = new Usuario();
            user.setNombre(panCrearUsuario.txtNombre.getText());
            user.setUser(panCrearUsuario.txtUser.getText());
            user.setPassword(panCrearUsuario.txtPass.getText());
            user.setFechaCommit(new Date());
            user.setHoraCommit(new Date());
            user.setEstado(1);
            user.setIdPrivilegio(DAOManager.getInstancia().getPrivilegioDAO().readOne(1));

            try {
                usuarioDAO.create(user);
                resetFormularioNuevoUsuario();
                MsjInfo.msjRegistroExitoso(panCrearUsuario);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(panCrearUsuario, e.getMessage());
            }

        }
    }

    private void mostrarEditarUsuario() {
        usuarioEditar = TablaUtil.getEntityFilaSeleccionada(
                panListUsuarios.tblUsuarios, 0, Usuario.class
        );
        if (usuarioEditar != null) {//validar que no sea null
            panEditarUsuario.txtNombre.setText(usuarioEditar.getNombre());
            panEditarUsuario.txtUser.setText(usuarioEditar.getUser());
            panEditarUsuario.txtPass.setText(usuarioEditar.getPassword());
            PanelUtil.cambiarPanel(IfrmMenuUsuarios.PANEL_CAMBIANTE, panEditarUsuario);
        }
    }

    private void actualizarUsuario() {
        //validar el formulario de proveedor
        if (ValidarJTextField.camposVacios(panEditarUsuario.txtNombre,
                panEditarUsuario.txtPass, panEditarUsuario.txtUser)) {
            MsjValidacion.msjJTextFieldRequeridos(panEditarUsuario);
        } else {

            /////editar usuario
            usuarioEditar.setNombre(panEditarUsuario.txtNombre.getText());
            usuarioEditar.setUser(panEditarUsuario.txtUser.getText());
            usuarioEditar.setPassword(panEditarUsuario.txtPass.getText());

            try {
                usuarioDAO.update(usuarioEditar);
                resetFormularioEditarUsuario();
                MsjInfo.msjRegistroExitoso(panEditarUsuario);
                consultarUsuarios();
                PanelUtil.cambiarPanel(IfrmMenuUsuarios.PANEL_CAMBIANTE, panListUsuarios);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(panEditarUsuario, e.getMessage());
            }

        }
    }

    private void activarUsuario() {

        usuarioEditar = TablaUtil.getEntityFilaSeleccionada(panListUsuarios.tblUsuarios, 0, Usuario.class);
        if (usuarioEditar != null) {
            if (usuarioEditar.getEstado() == 1) {//si esta activado
                MsjInfo.msjRegistroYaActivado(panListUsuarios);
            } else {//no activado
                usuarioEditar.setEstado(1);
                try {
                    usuarioDAO.update(usuarioEditar);
                    consultarUsuarios();
                } catch (Exception e) {
                    MsjException.msjErrorActualizar(panListUsuarios, e.getMessage());
                }
            }
        }
    }

    private void desactivarUsuario() {
        usuarioEditar = TablaUtil.getEntityFilaSeleccionada(panListUsuarios.tblUsuarios, 0, Usuario.class);
        if (usuarioEditar != null) {
            if (usuarioEditar.getEstado() == 0) {//si ya esta desactivado
                MsjInfo.msjRegistroYaDesactivado(panListUsuarios);
            } else {//activado
                //mensaje de confiramcion para desactivar
                if (MsjInfo.msjConfimacionDesactivar(panListUsuarios) == JOptionPane.YES_OPTION) {

                    usuarioEditar.setEstado(0);
                    try {
                        usuarioDAO.update(usuarioEditar);
                        consultarUsuarios();
                    } catch (Exception e) {
                        MsjException.msjErrorActualizar(panListUsuarios, e.getMessage());
                    }
                }
            }
        }
    }

}
