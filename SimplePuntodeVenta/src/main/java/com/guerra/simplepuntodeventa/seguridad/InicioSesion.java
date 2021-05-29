/*
 *logica para el inicio de sesion
 */
package com.guerra.simplepuntodeventa.seguridad;

import com.guerra.simplepuntodeventa.ControladorPrincipal;
import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.entidades.Cliente;
import com.guerra.simplepuntodeventa.modelo.entidades.Proveedor;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesUsuarios;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.vista.loguin.FrmLoguin;
import java.util.Arrays;

/**
 *
 * @author jaasiel
 */
public class InicioSesion {

    private final FrmLoguin loguin;
    private final ControladorPrincipal controladorPrincipal;

    public InicioSesion() {
        this.loguin = new FrmLoguin();
        this.controladorPrincipal = new ControladorPrincipal();
        init();
    }

    private void init() {
        resetFormulario();
        loguin.btnAcceder.addActionListener((ae) -> {
            iniciarSesion();
        });
        loguin.txtPassword.addActionListener((ae) -> {
            iniciarSesion();
        });
        loguin.setLocationRelativeTo(null);
        loguin.setVisible(true);
    }

    private void resetFormulario() {
        loguin.txtPassword.setText("");
        loguin.txtUser.setText("");
        loguin.txtUser.requestFocusInWindow();
    }

    ////////////eventos//////////////7
    private void iniciarSesion() {

        if (ValidarJTextField.camposVacios(loguin.txtUser, loguin.txtPassword)) {
            MsjValidacion.msjJTextFieldRequeridos(loguin);
        } else {

            Usuario u = FuncionesUsuarios.obtenerUsuario(loguin.txtUser.getText(),
                    String.valueOf(loguin.txtPassword.getPassword()));

            if (u == null) {
                MsjValidacion.msjCredencialesIncorrectos(loguin);
                resetFormulario();
                return;
            }

            loguin.dispose();// cerrar ventana loguin

            //crear cache en la sesion
            Usuario user = u;
            Cliente cliente = DAOManager.getInstancia().getClienteDAO().readOne(1); //publico general
            Proveedor prov = DAOManager.getInstancia().getProveedorDAO().readOne(1); //proveedor principal
            Session.getInstancia().setAttribute("user", user);
            Session.getInstancia().setAttribute("cliente", cliente);
            Session.getInstancia().setAttribute("proveedor", prov);

            controladorPrincipal.initPrograma();

        }
    }

}
