/*
 * Controlador para el modulo de servicios
 */
package com.guerra.simplepuntodeventa.controlador.servicios;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.ServicioDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Servicio;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.vista.servicios.IfrmServicios;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author jaasiel
 */
public class ControladorServicios {

    private final IfrmServicios ifrmServicios;
    private final ServicioDAOImpl servicioDAO = DAOManager.getInstancia().getServicioDAO();
    private Servicio servicioEditar;

    public ControladorServicios(IfrmServicios ifrmServicios) {
        this.ifrmServicios = ifrmServicios;
        init();
    }

    private void init() {
        resetFormulario();
        ComboBoxUtil.llenarComboEstado(ifrmServicios.cbxEstado);
        ifrmServicios.btnActivar.addActionListener((e) -> {
            activarCategoria();
        });
        ifrmServicios.btnDesactivar.addActionListener((e) -> {
            desactivarServicio();
        });
        ifrmServicios.btnActualizar.addActionListener((e) -> {
            actualizarServicio();
        });
        ifrmServicios.btnCancelar.addActionListener((e) -> {
            cancelarFormulario();
        });
        ifrmServicios.btnEditar.addActionListener((e) -> {
            editarServicio();
        });
        ifrmServicios.btnGuardar.addActionListener((e) -> {
            guardarServicio();
        });
        ifrmServicios.cbxEstado.addActionListener((e) -> {
            consultarServiciosEstado();
        });
    }

    private List<Servicio> listarServiciosEstado(int estado) {
        return servicioDAO.readByNameQuery("Servicio.findByEstado", "estado", estado);
    }

    /////////////metodos publicos////////////////
    public void consultarServicios() {
        ifrmServicios.cbxEstado.setSelectedIndex(0);
    }

    public void resetFormulario() {
        ifrmServicios.txtCodigo.setText("");
        ifrmServicios.txtDescripcion.setText("");
        ifrmServicios.btnActualizar.setEnabled(false);
        ifrmServicios.btnGuardar.setEnabled(true);
        ifrmServicios.spnPrecioA.setValue(0.00D);
        ifrmServicios.spnPrecioB.setValue(0.00D);
        ifrmServicios.spnPrecioC.setValue(0.00D);
        servicioEditar = null;
    }

    ////////////////eventos/////////////////
    private void activarCategoria() {

        servicioEditar = TablaUtil.getEntityFilaSeleccionada(ifrmServicios.tblServicios, 0, Servicio.class);
        if (servicioEditar != null) {
            if (servicioEditar.getEstado() == 0) {//si esta desactivado
                servicioEditar.setEstado(1);//activar
                try {
                    servicioDAO.update(servicioEditar);
                    consultarServicios();
                } catch (Exception ex) {
                    MsjException.msjErrorActualizar(ifrmServicios, ex.getMessage());
                }
            } else {
                MsjInfo.msjRegistroYaActivado(ifrmServicios);
            }
        }
    }

    private void desactivarServicio() {
        servicioEditar = TablaUtil.getEntityFilaSeleccionada(ifrmServicios.tblServicios, 0, Servicio.class);
        if (servicioEditar != null) {
            if (servicioEditar.getEstado() == 1) {// si esta activado
                if (MsjInfo.msjConfimacionDesactivar(ifrmServicios) == JOptionPane.YES_OPTION) {
                    servicioEditar.setEstado(0);// desactivar
                    try {
                        servicioDAO.update(servicioEditar);
                        consultarServicios();
                    } catch (Exception ex) {
                        MsjException.msjErrorActualizar(ifrmServicios, ex.getMessage());
                    }
                }
            } else {
                MsjInfo.msjRegistroYaDesactivado(ifrmServicios);
            }
        }
    }

    private void actualizarServicio() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            ifrmServicios.txtCodigo, ifrmServicios.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(ifrmServicios);
        } else {
            ////////formulario valido////////
            servicioEditar.setCodigo(ifrmServicios.txtCodigo.getText().trim());
            servicioEditar.setDescripcion(ifrmServicios.txtDescripcion.getText().trim());
            servicioEditar.setPrecioA((double) ifrmServicios.spnPrecioA.getValue());
            servicioEditar.setPrecioB((double) ifrmServicios.spnPrecioB.getValue());
            servicioEditar.setPrecioC((double) ifrmServicios.spnPrecioC.getValue());
            try {
                servicioDAO.update(servicioEditar);
                resetFormulario();
                consultarServicios();
                MsjInfo.msjActualizacionExitosa(ifrmServicios);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(ifrmServicios, e.getMessage());
            }
        }
    }

    private void cancelarFormulario() {
        resetFormulario();
    }

    private void editarServicio() {
        //poner una marca para editar en el formulario
        servicioEditar = TablaUtil.getEntityFilaSeleccionada(ifrmServicios.tblServicios, 0, Servicio.class);
        if (servicioEditar != null) {
            ifrmServicios.txtCodigo.setText(servicioEditar.getCodigo());
            ifrmServicios.txtDescripcion.setText(servicioEditar.getDescripcion());
            ifrmServicios.spnPrecioA.setValue(servicioEditar.getPrecioA());
            ifrmServicios.spnPrecioB.setValue(servicioEditar.getPrecioB());
            ifrmServicios.spnPrecioC.setValue(servicioEditar.getPrecioC());
            ifrmServicios.btnActualizar.setEnabled(true);
            ifrmServicios.btnGuardar.setEnabled(false);
        }
    }

    private void guardarServicio() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            ifrmServicios.txtCodigo, ifrmServicios.txtDescripcion
        })) {
            MsjValidacion.msjJTextFieldRequeridos(ifrmServicios);
        } else {
            ////////formulario valido////////
            Usuario user = (Usuario) Session.getInstancia().getAttribute("user");
            Servicio s = new Servicio();
            s.setCodigo(ifrmServicios.txtCodigo.getText().trim());
            s.setDescripcion(ifrmServicios.txtDescripcion.getText().trim());
            s.setPrecioA((double) ifrmServicios.spnPrecioA.getValue());
            s.setPrecioB((double) ifrmServicios.spnPrecioB.getValue());
            s.setPrecioC((double) ifrmServicios.spnPrecioC.getValue());
            s.setEstado(1);
            s.setFechaCommit(new Date());
            s.setHoraCommit(new Date());
            s.setIdUsuario(user);
            try {
                servicioDAO.create(s);
                resetFormulario();
                consultarServicios();
                MsjInfo.msjRegistroExitoso(ifrmServicios);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(ifrmServicios, e.getMessage());
            }
        }
    }

    private void consultarServiciosEstado() {
        Estado e = (Estado) ifrmServicios.cbxEstado.getSelectedItem();
        TablaUtil.llenarTablaConEntity(ifrmServicios.tblServicios, listarServiciosEstado(e.getValor()),
                new String[]{"codigo", "descripcion", "precioA", "precioB", "precioC"}, 0);
    }

}
