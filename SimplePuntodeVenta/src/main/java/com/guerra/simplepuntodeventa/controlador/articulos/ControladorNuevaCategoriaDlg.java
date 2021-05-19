/*
 * Crear nueva categoria desde el dialogo
 */
package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.dao.CategoriaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Categoria;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.DlgNuevaCategoria;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ControladorNuevaCategoriaDlg {

    private final DlgNuevaCategoria dlgNuevaCategoria;
    private final JComboBox cbxCategorias;

    private final CategoriaDAOImpl categoriaDAO = DAOManager.getInstancia().getCategoriaDAO();

    public ControladorNuevaCategoriaDlg(DlgNuevaCategoria dlgNuevaCategoria, JComboBox cbxCategorias) {
        this.dlgNuevaCategoria = dlgNuevaCategoria;
        this.cbxCategorias = cbxCategorias;
        init();
    }

    private void init() {
        dlgNuevaCategoria.btnGuardar.addActionListener((e) -> {
            guardarCategoria();
        });
    }

    private void consultarCategorias() {
        ComboBoxUtil.llenarComboDatos(
                cbxCategorias,
                categoriaDAO.readByNameQuery("Categoria.findByEstado", "estado", 1)
        );
    }

    /////////metodos publicos///////////////
    public void resetFormulario() {
        dlgNuevaCategoria.txtNombre.setText("");
        dlgNuevaCategoria.txtDescripcion.setText("");
    }

    ////////////eventos//////////////////
    private void guardarCategoria() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            dlgNuevaCategoria.txtNombre, dlgNuevaCategoria.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(dlgNuevaCategoria);
        } else {
            ////////formulario valido////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Categoria c = new Categoria();
            c.setNombre(dlgNuevaCategoria.txtNombre.getText().trim());
            c.setDescripcion(dlgNuevaCategoria.txtDescripcion.getText().trim());
            c.setEstado(1);
            c.setFechaCommit(new Date());
            c.setHoraCommit(new Date());
            c.setIdUsuario(user);
            try {
                categoriaDAO.create(c);
                resetFormulario();
                consultarCategorias();
                MsjInfo.msjRegistroExitoso(dlgNuevaCategoria);
                dlgNuevaCategoria.dispose();
            } catch (Exception e) {
                MsjException.msjErrorGuardar(dlgNuevaCategoria, e.getMessage());
            }
        }
    }

}
