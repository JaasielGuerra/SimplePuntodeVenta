/*
 * Eventos para las vistas de las Marcas
 */
package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.MarcaDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Marca;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.PanMarcas;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ControladorMarcas {

    private final PanMarcas panMarcas;
    private final MarcaDAOImpl marcaDAO = DAOManager.getInstancia().getMarcaDAO();
    private Marca marcaEditar;

    public ControladorMarcas(PanMarcas panMarcas) {
        this.panMarcas = panMarcas;
        initComponents();
    }

    private void initComponents() {
        resetFormulario();
        ComboBoxUtil.llenarComboEstado(panMarcas.cbxEstado);
        panMarcas.btnGuardar.addActionListener((e) -> {
            guardarMarca();
        });
        panMarcas.btnActivar.addActionListener((e) -> {
            activarMarca();
        });
        panMarcas.btnActualizar.addActionListener((e) -> {
            actualizarMarca();
        });
        panMarcas.btnCancelar.addActionListener((e) -> {
            cancelarFormulario();
        });
        panMarcas.btnDesactivar.addActionListener((e) -> {
            desactivarMarca();
        });
        panMarcas.btnEditar.addActionListener((e) -> {
            editarMarca();
        });
        panMarcas.cbxEstado.addActionListener((e) -> {
            consultarMarcaEstado();
        });
    }

    private List<Marca> listarMarcasEstado(int estado) {
        return marcaDAO.readByNameQuery("Marca.findByEstado", "estado", estado);
    }

    ////////metodos publicos///////////
    public void consultarMarcas() {
        panMarcas.cbxEstado.setSelectedIndex(0);
    }

    public void resetFormulario() {
        panMarcas.txtNombre.setText("");
        panMarcas.txtDescripcion.setText("");
        panMarcas.btnActualizar.setEnabled(false);
        panMarcas.btnGuardar.setEnabled(true);
        marcaEditar = null;

    }

    /////////////eventos/////////////
    private void guardarMarca() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panMarcas.txtNombre, panMarcas.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panMarcas);
        } else {
            ////////formulario valido////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Marca m = new Marca();
            m.setNombre(panMarcas.txtNombre.getText().trim());
            m.setDescripcion(panMarcas.txtDescripcion.getText().trim());
            m.setEstado(1);
            m.setFechaCommit(new Date());
            m.setHoraCommit(new Date());
            m.setIdUsuario(user);
            try {
                marcaDAO.create(m);
                resetFormulario();
                consultarMarcas();
                MsjInfo.msjRegistroExitoso(panMarcas);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(panMarcas, e.getMessage());
            }
        }
    }

    private void activarMarca() {
        marcaEditar = TablaUtil.getEntityFilaSeleccionada(panMarcas.tblMarcas, 0, Marca.class);
        if (marcaEditar != null) {
            if (marcaEditar.getEstado() == 0) {//si esta desactivado
                marcaEditar.setEstado(1);//activar
                try {
                    marcaDAO.update(marcaEditar);
                    consultarMarcas();
                } catch (Exception ex) {
                    MsjException.msjErrorActualizar(panMarcas, ex.getMessage());
                }
            } else {
                MsjInfo.msjRegistroYaActivado(panMarcas);
            }
        }
    }

    private void actualizarMarca() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panMarcas.txtNombre, panMarcas.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panMarcas);
        } else {
            ////////actualizar////////
            marcaEditar.setNombre(panMarcas.txtNombre.getText().trim());
            marcaEditar.setDescripcion(panMarcas.txtDescripcion.getText().trim());
            try {
                marcaDAO.update(marcaEditar);
                resetFormulario();
                consultarMarcas();
                MsjInfo.msjActualizacionExitosa(panMarcas);
            } catch (Exception e) {
                MsjException.msjErrorActualizar(panMarcas, e.getMessage());
            }
        }
    }

    private void cancelarFormulario() {
        resetFormulario();
    }

    private void desactivarMarca() {
        marcaEditar = TablaUtil.getEntityFilaSeleccionada(panMarcas.tblMarcas, 0, Marca.class);
        if (marcaEditar != null) {
            if (marcaEditar.getEstado() == 1) {// si esta activado
                if (MsjInfo.msjConfimacionDesactivar(panMarcas) == JOptionPane.YES_OPTION) {
                    marcaEditar.setEstado(0);// desactivar
                    try {
                        marcaDAO.update(marcaEditar);
                        consultarMarcas();
                    } catch (Exception ex) {
                        MsjException.msjErrorActualizar(panMarcas, ex.getMessage());
                    }
                }
            } else {
                MsjInfo.msjRegistroYaDesactivado(panMarcas);
            }
        }
    }

    private void editarMarca() {
        //poner una marca para editar en el formulario
        marcaEditar = TablaUtil.getEntityFilaSeleccionada(panMarcas.tblMarcas, 0, Marca.class);
        if (marcaEditar != null) {
            panMarcas.txtNombre.setText(marcaEditar.getNombre());
            panMarcas.txtDescripcion.setText(marcaEditar.getDescripcion());
            panMarcas.btnActualizar.setEnabled(true);
            panMarcas.btnGuardar.setEnabled(false);
        }
    }

    private void consultarMarcaEstado() {
        Estado e = (Estado) panMarcas.cbxEstado.getSelectedItem();
        TablaUtil.llenarTablaConEntity(panMarcas.tblMarcas, listarMarcasEstado(e.getValor()),
                new String[]{"nombre", "descripcion", "estado"}, 0);
    }

}
