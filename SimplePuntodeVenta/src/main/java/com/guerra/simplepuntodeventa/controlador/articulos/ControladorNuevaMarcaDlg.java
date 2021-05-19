/*
 * Crear nueva marca desde el dialogo
 */
package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.dao.MarcaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Marca;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.DlgNuevaMarca;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ControladorNuevaMarcaDlg {

    private final DlgNuevaMarca dlgNuevaMarca;
    private final JComboBox cbxMarcas;

    private final MarcaDAOImpl marcaDAO = DAOManager.getInstancia().getMarcaDAO();

    public ControladorNuevaMarcaDlg(DlgNuevaMarca dlgNuevaMarca, JComboBox cbxMarcas) {
        this.dlgNuevaMarca = dlgNuevaMarca;
        this.cbxMarcas = cbxMarcas;
        init();
    }

    private void init() {
        dlgNuevaMarca.btnGuardar.addActionListener((e) -> {
            guardarMarca();
        });
    }

    private void consultarMarcas() {
        ComboBoxUtil.llenarComboDatos(
                cbxMarcas,
                marcaDAO.readByNameQuery("Marca.findByEstado", "estado", 1)
        );
    }

    //////////metodos publicos////////////
    public void resetFormulario() {
        dlgNuevaMarca.txtNombre.setText("");
        dlgNuevaMarca.txtDescripcion.setText("");

    }

    /////////////eventos/////////////////
    private void guardarMarca() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            dlgNuevaMarca.txtNombre, dlgNuevaMarca.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(dlgNuevaMarca);
        } else {
            ////////formulario valido////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Marca m = new Marca();
            m.setNombre(dlgNuevaMarca.txtNombre.getText().trim());
            m.setDescripcion(dlgNuevaMarca.txtDescripcion.getText().trim());
            m.setEstado(1);
            m.setFechaCommit(new Date());
            m.setHoraCommit(new Date());
            m.setIdUsuario(user);
            try {
                marcaDAO.create(m);
                resetFormulario();
                consultarMarcas();
                MsjInfo.msjRegistroExitoso(dlgNuevaMarca);
                dlgNuevaMarca.dispose();
            } catch (Exception e) {
                MsjException.msjErrorGuardar(dlgNuevaMarca, e.getMessage());
            }
        }
    }

}
