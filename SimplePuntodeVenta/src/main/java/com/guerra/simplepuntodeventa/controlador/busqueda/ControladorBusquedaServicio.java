/*
 * Controlador para el dialogo de busqueda de un servicio
 */
package com.guerra.simplepuntodeventa.controlador.busqueda;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.dao.ServicioDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Servicio;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesServicios;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.vista.ventas.DlgBuscarServicio;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Jaasiel
 */
public class ControladorBusquedaServicio {

    private final DlgBuscarServicio dlgBuscarServicio;
    private final ISeleccion seleccion;
    private final Component localizacion;

    private final ServicioDAOImpl servicioDAO = DAOManager.getInstancia().getServicioDAO();

    public ControladorBusquedaServicio(Component localizacion, ISeleccion<Servicio> seleccion) {
        this.dlgBuscarServicio = new DlgBuscarServicio(null, true);
        this.seleccion = seleccion;
        this.localizacion = localizacion;
        init();
    }

    private void init() {
        dlgBuscarServicio.txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarServicioDescripcion();
            }
        });
        dlgBuscarServicio.btnCancelar.addActionListener((e) -> {
            dlgBuscarServicio.dispose();
        });
        dlgBuscarServicio.btnSeleccionar.addActionListener((e) -> {
            seleccionarServicioDlgBusqueda();
        });
    }

    //llenar la tabla de articulos del dialogo de busqueda a partir de lista
    private void llenaTablaServiciosDlgBusqueda(List<Servicio> servicios, JTable tabla) {
        TablaUtil.llenarTablaConEntity(tabla,
                servicios, new String[]{
                    "codigo", "descripcion", "precioA", "precioB", "precioC"
                }, 0);
    }

    /////////////metodos publicos///////////////////
    /**
     * Leventa el dialogo de busqueda
     */
    public void mostrarDialogoBuscarServicio() {
        //llenar tabla servicios dialogo de busqueda con servicios activos
        dlgBuscarServicio.txtBuscar.setText("");// limpiar campo de busqueda
        List<Servicio> servicios = servicioDAO.readByNameQuery("Servicio.findByEstado", "estado", 1);
        llenaTablaServiciosDlgBusqueda(servicios, dlgBuscarServicio.tblServicios);
        dlgBuscarServicio.setLocationRelativeTo(localizacion);
        dlgBuscarServicio.setVisible(true);
    }

    ////////////eventos//////////
    private void buscarServicioDescripcion() {
        List<Servicio> servicios = FuncionesServicios
                .buscarServicioPorDescripcionEstado(
                        1, dlgBuscarServicio.txtBuscar.getText().trim()
                );
        llenaTablaServiciosDlgBusqueda(servicios, dlgBuscarServicio.tblServicios);
    }

    private void seleccionarServicioDlgBusqueda() {
        Servicio ser = TablaUtil.getEntityFilaSeleccionada(
                dlgBuscarServicio.tblServicios, 0, Servicio.class
        );
        if (ser != null) {
            dlgBuscarServicio.dispose();
            seleccion.articuloSelecccionado(ser);
        }
    }
}
