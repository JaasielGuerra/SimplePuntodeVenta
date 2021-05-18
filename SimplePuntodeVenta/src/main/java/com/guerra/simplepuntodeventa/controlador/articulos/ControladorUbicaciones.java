package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.UbicacionDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Ubicacion;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.PanUbicaciones;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ControladorUbicaciones {

    private final PanUbicaciones panUbicaciones;
    private final UbicacionDAOImpl ubicacionDAO = DAOManager.getInstancia().getUbicacionDAO();
    private Ubicacion ubicacionEditar;

    public ControladorUbicaciones(PanUbicaciones panUbicaciones) {
        this.panUbicaciones = panUbicaciones;
        initComponents();
    }

    private void initComponents() {
        resetFormulario();
        ComboBoxUtil.llenarComboEstado(panUbicaciones.cbxEstado);
        panUbicaciones.btnActivar.addActionListener((e) -> {
            activarUbicacion();
        });
        panUbicaciones.btnDesactivar.addActionListener((e) -> {
            desactivarUbicacion();
        });
        panUbicaciones.btnActualizar.addActionListener((e) -> {
            actualizarUbicacion();
        });
        panUbicaciones.btnCancelar.addActionListener((e) -> {
            cancelarFormulario();
        });
        panUbicaciones.btnEditar.addActionListener((e) -> {
            editarUbicacion();
        });
        panUbicaciones.btnGuardar.addActionListener((e) -> {
            guardarUbicacion();
        });
        panUbicaciones.cbxEstado.addActionListener((e) -> {
            consultarUbicacionesEstado();
        });
    }

    private List<Ubicacion> listarUbicadionesEstado(int estado) {
        return ubicacionDAO.readByNameQuery("Ubicacion.findByEstado", "estado", estado);
    }

    //////////metodos publicos////////////
    public void consultarUbicaciones() {
        panUbicaciones.cbxEstado.setSelectedIndex(0);
    }

    public void resetFormulario() {
        panUbicaciones.txtNombre.setText("");
        panUbicaciones.txtDescripcion.setText("");
        panUbicaciones.btnActualizar.setEnabled(false);
        panUbicaciones.btnGuardar.setEnabled(true);
        ubicacionEditar = null;
    }

    ///////////eventos////////////////
    private void activarUbicacion() {
        ubicacionEditar = TablaUtil.getEntityFilaSeleccionada(panUbicaciones.tblUbicaciones, 0, Ubicacion.class);
        if (ubicacionEditar != null) {
            if (ubicacionEditar.getEstado() == 0) {//si esta desactivado
                ubicacionEditar.setEstado(1);//activar
                try {
                    ubicacionDAO.update(ubicacionEditar);
                    consultarUbicaciones();
                } catch (Exception ex) {
                    MsjException.msjErrorActualizar(panUbicaciones, ex.getMessage());
                }
            } else {
                MsjInfo.msjRegistroYaActivado(panUbicaciones);
            }
        }
    }

    private void desactivarUbicacion() {
        ubicacionEditar = TablaUtil.getEntityFilaSeleccionada(panUbicaciones.tblUbicaciones, 0, Ubicacion.class);
        if (ubicacionEditar != null) {
            if (ubicacionEditar.getEstado() == 1) {// si esta activado
                if (MsjInfo.msjConfimacionDesactivar(panUbicaciones) == JOptionPane.YES_OPTION) {
                    ubicacionEditar.setEstado(0);// desactivar
                    try {
                        ubicacionDAO.update(ubicacionEditar);
                        consultarUbicaciones();
                    } catch (Exception ex) {
                        MsjException.msjErrorActualizar(panUbicaciones, ex.getMessage());
                    }
                }
            } else {
                MsjInfo.msjRegistroYaDesactivado(panUbicaciones);
            }
        }
    }

    private void actualizarUbicacion() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panUbicaciones.txtNombre, panUbicaciones.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panUbicaciones);
        } else {
            ////////formulario valido////////
            ubicacionEditar.setNombre(panUbicaciones.txtNombre.getText().trim());
            ubicacionEditar.setDescripcion(panUbicaciones.txtDescripcion.getText().trim());
            try {
                ubicacionDAO.update(ubicacionEditar);
                resetFormulario();
                consultarUbicaciones();
                MsjInfo.msjActualizacionExitosa(panUbicaciones);
            } catch (Exception e) {
                MsjException.msjErrorActualizar(panUbicaciones, e.getMessage());
            }
        }
    }

    private void cancelarFormulario() {
        resetFormulario();
    }

    private void editarUbicacion() {
        //poner una marca para editar en el formulario
        ubicacionEditar = TablaUtil.getEntityFilaSeleccionada(panUbicaciones.tblUbicaciones, 0, Ubicacion.class);
        if (ubicacionDAO != null) {
            panUbicaciones.txtNombre.setText(ubicacionEditar.getNombre());
            panUbicaciones.txtDescripcion.setText(ubicacionEditar.getDescripcion());
            panUbicaciones.btnActualizar.setEnabled(true);
            panUbicaciones.btnGuardar.setEnabled(false);
        }
    }

    private void guardarUbicacion() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panUbicaciones.txtNombre, panUbicaciones.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panUbicaciones);
        } else {
            ////////formulario valido////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Ubicacion c = new Ubicacion();
            c.setNombre(panUbicaciones.txtNombre.getText().trim());
            c.setDescripcion(panUbicaciones.txtDescripcion.getText().trim());
            c.setEstado(1);
            c.setFechaCommit(new Date());
            c.setHoraCommit(new Date());
            c.setIdUsuario(user);
            try {
                ubicacionDAO.create(c);
                resetFormulario();
                consultarUbicaciones();
                MsjInfo.msjRegistroExitoso(panUbicaciones);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(panUbicaciones, e.getMessage());
            }
        }
    }

    private void consultarUbicacionesEstado() {
        Estado e = (Estado) panUbicaciones.cbxEstado.getSelectedItem();
        TablaUtil.llenarTablaConEntity(panUbicaciones.tblUbicaciones, listarUbicadionesEstado(e.getValor()),
                new String[]{"nombre", "descripcion", "estado"}, 0);
    }

}
