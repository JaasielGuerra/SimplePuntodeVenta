/*
 * Crear nueva ubicacion desde el dialogo
 */
package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.dao.UbicacionDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Ubicacion;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.DlgNuevaUbicacion;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ControladorNuevaUbicacion {

    private final DlgNuevaUbicacion dlgNuevaUbicacion;
    private final JComboBox cbxUbicaciones;

    private final UbicacionDAOImpl ubicacionDAO = DAOManager.getInstancia().getUbicacionDAO();

    public ControladorNuevaUbicacion(DlgNuevaUbicacion dlgNuevaUbicacion, JComboBox cbxUbicaciones) {
        this.dlgNuevaUbicacion = dlgNuevaUbicacion;
        this.cbxUbicaciones = cbxUbicaciones;
        init();
    }

    private void init() {
        dlgNuevaUbicacion.btnGuardar.addActionListener((e) -> {
            guardarUbicacion();
        });
    }

    private void consultarUbicaciones() {
        ComboBoxUtil.llenarComboDatos(
                cbxUbicaciones,
                ubicacionDAO.readByNameQuery("Ubicacion.findByEstado", "estado", 1)
        );
    }

    //////////metodos publicos////////////
    public void resetFormulario() {
        dlgNuevaUbicacion.txtNombre.setText("");
        dlgNuevaUbicacion.txtDescripcion.setText("");

    }

    /////////////eventos/////////////////
    private void guardarUbicacion() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            dlgNuevaUbicacion.txtNombre, dlgNuevaUbicacion.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(dlgNuevaUbicacion);
        } else {
            ////////formulario valido////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Ubicacion u = new Ubicacion();
            u.setNombre(dlgNuevaUbicacion.txtNombre.getText().trim());
            u.setDescripcion(dlgNuevaUbicacion.txtDescripcion.getText().trim());
            u.setEstado(1);
            u.setFechaCommit(new Date());
            u.setHoraCommit(new Date());
            u.setIdUsuario(user);
            try {
                ubicacionDAO.create(u);
                resetFormulario();
                consultarUbicaciones();
                MsjInfo.msjRegistroExitoso(dlgNuevaUbicacion);
                dlgNuevaUbicacion.dispose();
            } catch (Exception e) {
                MsjException.msjErrorGuardar(dlgNuevaUbicacion, e.getMessage());
            }
        }
    }

}
