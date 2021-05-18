package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.CategoriaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Categoria;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.PanCategorias;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ControladorCategorias {

    private final PanCategorias panCategorias;
    private final CategoriaDAOImpl categoriaDAO = DAOManager.getInstancia().getCategoriaDAO();
    private Categoria categoriaEditar;

    public ControladorCategorias(PanCategorias panCategorias) {
        this.panCategorias = panCategorias;
        initComponents();
    }

    private void initComponents() {
        resetFormulario();
        ComboBoxUtil.llenarComboEstado(panCategorias.cbxEstado);
        panCategorias.btnActivar.addActionListener((e) -> {
            activarCategoria();
        });
        panCategorias.btnDesactivar.addActionListener((e) -> {
            desactivarCategoria();
        });
        panCategorias.btnActualizar.addActionListener((e) -> {
            actualizarCategoria();
        });
        panCategorias.btnCancelar.addActionListener((e) -> {
            cancelarFormulario();
        });
        panCategorias.btnEditar.addActionListener((e) -> {
            editarCategoria();
        });
        panCategorias.btnGuardar.addActionListener((e) -> {
            guardarCategoria();
        });
        panCategorias.cbxEstado.addActionListener((e) -> {
            consultarCategoriasEstado();
        });
    }

    private List<Categoria> listarMarcasEstado(int estado) {
        return categoriaDAO.readByNameQuery("Categoria.findByEstado", "estado", estado);
    }

    //////////metodos publicos////////////
    public void consultarCategorias() {
        panCategorias.cbxEstado.setSelectedIndex(0);
    }

    public void resetFormulario() {
        panCategorias.txtNombre.setText("");
        panCategorias.txtDescripcion.setText("");
        panCategorias.btnActualizar.setEnabled(false);
        panCategorias.btnGuardar.setEnabled(true);
        categoriaEditar = null;
    }

    ///////////eventos////////////////
    private void activarCategoria() {
        categoriaEditar = TablaUtil.getEntityFilaSeleccionada(panCategorias.tblCate, 0, Categoria.class);
        if (categoriaEditar != null) {
            if (categoriaEditar.getEstado() == 0) {//si esta desactivado
                categoriaEditar.setEstado(1);//activar
                try {
                    categoriaDAO.update(categoriaEditar);
                    consultarCategorias();
                } catch (Exception ex) {
                    MsjException.msjErrorActualizar(panCategorias, ex.getMessage());
                }
            } else {
                MsjInfo.msjRegistroYaActivado(panCategorias);
            }
        }
    }

    private void desactivarCategoria() {
        categoriaEditar = TablaUtil.getEntityFilaSeleccionada(panCategorias.tblCate, 0, Categoria.class);
        if (categoriaEditar != null) {
            if (categoriaEditar.getEstado() == 1) {// si esta activado
                if (MsjInfo.msjConfimacionDesactivar(panCategorias) == JOptionPane.YES_OPTION) {
                    categoriaEditar.setEstado(0);// desactivar
                    try {
                        categoriaDAO.update(categoriaEditar);
                        consultarCategorias();
                    } catch (Exception ex) {
                        MsjException.msjErrorActualizar(panCategorias, ex.getMessage());
                    }
                }
            } else {
                MsjInfo.msjRegistroYaDesactivado(panCategorias);
            }
        }
    }

    private void actualizarCategoria() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panCategorias.txtNombre, panCategorias.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panCategorias);
        } else {
            ////////formulario valido////////
            categoriaEditar.setNombre(panCategorias.txtNombre.getText().trim());
            categoriaEditar.setDescripcion(panCategorias.txtDescripcion.getText().trim());
            try {
                categoriaDAO.update(categoriaEditar);
                resetFormulario();
                consultarCategorias();
                MsjInfo.msjActualizacionExitosa(panCategorias);
            } catch (Exception e) {
                MsjException.msjErrorActualizar(panCategorias, e.getMessage());
            }
        }
    }

    private void cancelarFormulario() {
        resetFormulario();
    }

    private void editarCategoria() {
        //poner una marca para editar en el formulario
        categoriaEditar = TablaUtil.getEntityFilaSeleccionada(panCategorias.tblCate, 0, Categoria.class);
        if (categoriaEditar != null) {
            panCategorias.txtNombre.setText(categoriaEditar.getNombre());
            panCategorias.txtDescripcion.setText(categoriaEditar.getDescripcion());
            panCategorias.btnActualizar.setEnabled(true);
            panCategorias.btnGuardar.setEnabled(false);
        }
    }

    private void guardarCategoria() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panCategorias.txtNombre, panCategorias.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panCategorias);
        } else {
            ////////formulario valido////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Categoria c = new Categoria();
            c.setNombre(panCategorias.txtNombre.getText().trim());
            c.setDescripcion(panCategorias.txtDescripcion.getText().trim());
            c.setEstado(1);
            c.setFechaCommit(new Date());
            c.setHoraCommit(new Date());
            c.setIdUsuario(user);
            try {
                categoriaDAO.create(c);
                resetFormulario();
                consultarCategorias();
                MsjInfo.msjRegistroExitoso(panCategorias);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(panCategorias, e.getMessage());
            }
        }
    }

    private void consultarCategoriasEstado() {
        Estado e = (Estado) panCategorias.cbxEstado.getSelectedItem();
        TablaUtil.llenarTablaConEntity(panCategorias.tblCate, listarMarcasEstado(e.getValor()),
                new String[]{"nombre", "descripcion", "estado"}, 0);
    }

}
