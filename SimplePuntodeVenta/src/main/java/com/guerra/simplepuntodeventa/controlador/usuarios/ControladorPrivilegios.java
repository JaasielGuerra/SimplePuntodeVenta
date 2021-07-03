/*
 * Eventos para las vistas de las Marcas
 */
package com.guerra.simplepuntodeventa.controlador.usuarios;

import com.guerra.simplepuntodeventa.global.Session;
import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.Estado;
import com.guerra.simplepuntodeventa.modelo.dao.PrivilegioDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Marca;
import com.guerra.simplepuntodeventa.modelo.entidades.Privilegio;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;
import com.guerra.simplepuntodeventa.recursos.utilerias.ComboBoxUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.recursos.utilerias.ValidarJTextField;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjException;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjInfo;
import com.guerra.simplepuntodeventa.recursos.mensajes.MsjValidacion;
import com.guerra.simplepuntodeventa.vista.usuarios.PanPrivilegios;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ControladorPrivilegios {

    private final PanPrivilegios panPrivilegios;
    private final PrivilegioDAOImpl privilegioDAO = DAOManager.getInstancia().getPrivilegioDAO();
    private Privilegio privilegioEditar;

    public ControladorPrivilegios(PanPrivilegios panPrivilegios) {
        this.panPrivilegios = panPrivilegios;
        initComponents();
    }

    private void initComponents() {
        resetFormulario();
        ComboBoxUtil.llenarComboEstado(panPrivilegios.cbxEstado);
        panPrivilegios.btnGuardar.addActionListener((e) -> {
            guardarPrivilegio();
        });
        panPrivilegios.btnActivar.addActionListener((e) -> {
            activarPrivilegio();
        });
        panPrivilegios.btnActualizar.addActionListener((e) -> {
            actualizarPrivilegios();
        });
        panPrivilegios.btnCancelar.addActionListener((e) -> {
            cancelarFormulario();
        });
        panPrivilegios.btnDesactivar.addActionListener((e) -> {
            desactivarPrivilegio();
        });
        panPrivilegios.btnEditar.addActionListener((e) -> {
            editarMarca();
        });
        panPrivilegios.cbxEstado.addActionListener((e) -> {
            consultarMarcaEstado();
        });
    }

    private List<Privilegio> listarPrivilegiosEstado(int estado) {
        return privilegioDAO.readByNameQuery("Privilegio.findByEstado", "estado", estado);
    }

    ////////metodos publicos///////////
    public void consultarPrivilegios() {
        panPrivilegios.cbxEstado.setSelectedIndex(0);
    }

    public void resetFormulario() {
        panPrivilegios.txtNombre.setText("");
        panPrivilegios.btnActualizar.setEnabled(false);
        panPrivilegios.btnGuardar.setEnabled(true);
        panPrivilegios.cbxVentas.setSelected(false);
        panPrivilegios.cbxArticulos.setSelected(false);
        panPrivilegios.cbxInventario.setSelected(false);
        panPrivilegios.cbxCompras.setSelected(false);
        panPrivilegios.cbxClientes.setSelected(false);
        panPrivilegios.cbxReportes.setSelected(false);
        panPrivilegios.cbxConfiguracion.setSelected(false);
        panPrivilegios.cbxServicio.setSelected(false);
        panPrivilegios.cbxProveedores.setSelected(false);
        
        privilegioEditar = null;

    }

    /////////////eventos/////////////
    private void guardarPrivilegio() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panPrivilegios.txtNombre
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panPrivilegios);
        } else {
            ////////formulario valido////////
            Privilegio p = new Privilegio();
            p.setDescripcion(panPrivilegios.txtNombre.getText().trim());
            p.setEstado(1);
            p.setFechaCommit(new Date());
            p.setHoraCommit(new Date());
            p.setVentas(panPrivilegios.cbxVentas.isSelected() ? 1 : 0);
            p.setArticulos(panPrivilegios.cbxArticulos.isSelected() ? 1 : 0);
            p.setInventario(panPrivilegios.cbxInventario.isSelected() ? 1 : 0);
            p.setCompras(panPrivilegios.cbxCompras.isSelected() ? 1 : 0);
            p.setClientes(panPrivilegios.cbxClientes.isSelected() ? 1 : 0);
            p.setReportes(panPrivilegios.cbxReportes.isSelected() ? 1 : 0);
            p.setConfiguracion(panPrivilegios.cbxConfiguracion.isSelected() ? 1 : 0);
            p.setServicio(panPrivilegios.cbxServicio.isSelected() ? 1 : 0);
            p.setProveedores(panPrivilegios.cbxProveedores.isSelected() ? 1 : 0);

            try {
                privilegioDAO.create(p);
                resetFormulario();
                consultarPrivilegios();
                MsjInfo.msjRegistroExitoso(panPrivilegios);
            } catch (Exception e) {
                MsjException.msjErrorGuardar(panPrivilegios, e.getMessage());
            }
        }
    }

    private void activarPrivilegio() {
        privilegioEditar = TablaUtil.getEntityFilaSeleccionada(panPrivilegios.tblPrivilegios, 0, Privilegio.class);
        if (privilegioEditar != null) {
            if (privilegioEditar.getEstado() == 0) {//si esta desactivado
                privilegioEditar.setEstado(1);//activar
                try {
                    privilegioDAO.update(privilegioEditar);
                    consultarPrivilegios();
                } catch (Exception ex) {
                    MsjException.msjErrorActualizar(panPrivilegios, ex.getMessage());
                }
            } else {
                MsjInfo.msjRegistroYaActivado(panPrivilegios);
            }
        }
    }

    private void actualizarPrivilegios() {
        if (ValidarJTextField.camposVacios(new JTextField[]{
            panPrivilegios.txtNombre
        })) {
            MsjValidacion.msjJTextFieldRequeridos(panPrivilegios);
        } else {
            ////////actualizar////////
            privilegioEditar.setDescripcion(panPrivilegios.txtNombre.getText().trim());
            privilegioEditar.setVentas(panPrivilegios.cbxVentas.isSelected() ? 1 : 0);
            privilegioEditar.setArticulos(panPrivilegios.cbxArticulos.isSelected() ? 1 : 0);
            privilegioEditar.setInventario(panPrivilegios.cbxInventario.isSelected() ? 1 : 0);
            privilegioEditar.setCompras(panPrivilegios.cbxCompras.isSelected() ? 1 : 0);
            privilegioEditar.setClientes(panPrivilegios.cbxClientes.isSelected() ? 1 : 0);
            privilegioEditar.setReportes(panPrivilegios.cbxReportes.isSelected() ? 1 : 0);
            privilegioEditar.setConfiguracion(panPrivilegios.cbxConfiguracion.isSelected() ? 1 : 0);
            privilegioEditar.setServicio(panPrivilegios.cbxServicio.isSelected() ? 1 : 0);
            privilegioEditar.setProveedores(panPrivilegios.cbxProveedores.isSelected() ? 1 : 0);
            try {
                privilegioDAO.update(privilegioEditar);
                resetFormulario();
                consultarPrivilegios();
                MsjInfo.msjActualizacionExitosa(panPrivilegios);
            } catch (Exception e) {
                MsjException.msjErrorActualizar(panPrivilegios, e.getMessage());
            }
        }
    }

    private void cancelarFormulario() {
        resetFormulario();
    }

    private void desactivarPrivilegio() {
        privilegioEditar = TablaUtil.getEntityFilaSeleccionada(panPrivilegios.tblPrivilegios, 0, Privilegio.class);
        if (privilegioEditar != null) {
            if (privilegioEditar.getEstado() == 1) {// si esta activado
                if (MsjInfo.msjConfimacionDesactivar(panPrivilegios) == JOptionPane.YES_OPTION) {
                    privilegioEditar.setEstado(0);// desactivar
                    try {
                        privilegioDAO.update(privilegioEditar);
                        consultarPrivilegios();
                    } catch (Exception ex) {
                        MsjException.msjErrorActualizar(panPrivilegios, ex.getMessage());
                    }
                }
            } else {
                MsjInfo.msjRegistroYaDesactivado(panPrivilegios);
            }
        }
    }

    private void editarMarca() {
        //poner una marca para editar en el formulario
        privilegioEditar = TablaUtil.getEntityFilaSeleccionada(panPrivilegios.tblPrivilegios, 0, Privilegio.class);
        if (privilegioEditar != null) {
            panPrivilegios.txtNombre.setText(privilegioEditar.getDescripcion());
            panPrivilegios.cbxVentas.setSelected((privilegioEditar.getVentas() == 1));
            panPrivilegios.cbxArticulos.setSelected((privilegioEditar.getArticulos() == 1));
            panPrivilegios.cbxInventario.setSelected((privilegioEditar.getInventario() == 1));
            panPrivilegios.cbxCompras.setSelected((privilegioEditar.getCompras() == 1));
            panPrivilegios.cbxClientes.setSelected((privilegioEditar.getClientes() == 1));
            panPrivilegios.cbxReportes.setSelected((privilegioEditar.getReportes() == 1));
            panPrivilegios.cbxConfiguracion.setSelected((privilegioEditar.getConfiguracion() == 1));
            panPrivilegios.cbxServicio.setSelected((privilegioEditar.getServicio() == 1));
            panPrivilegios.cbxProveedores.setSelected((privilegioEditar.getProveedores() == 1));
            panPrivilegios.btnActualizar.setEnabled(true);
            panPrivilegios.btnGuardar.setEnabled(false);
        }
    }

    private void consultarMarcaEstado() {
        Estado e = (Estado) panPrivilegios.cbxEstado.getSelectedItem();
        TablaUtil.llenarTablaConEntity(panPrivilegios.tblPrivilegios, listarPrivilegiosEstado(e.getValor()),
                new String[]{"descripcion", "estado"}, 0);
    }

}
