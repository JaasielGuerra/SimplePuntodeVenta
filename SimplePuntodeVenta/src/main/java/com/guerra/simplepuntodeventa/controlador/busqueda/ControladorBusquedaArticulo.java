/*
 * Controlador para el dialogo de busqueda de un articulo
 */
package com.guerra.simplepuntodeventa.controlador.busqueda;

import com.guerra.simplepuntodeventa.modelo.DAOManager;
import com.guerra.simplepuntodeventa.modelo.dao.ArticuloDAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;
import com.guerra.simplepuntodeventa.modelo.funciones.FuncionesArticulos;
import com.guerra.simplepuntodeventa.recursos.utilerias.TablaUtil;
import com.guerra.simplepuntodeventa.vista.inventario.DlgBuscarArticulo;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Jaasiel
 */
public class ControladorBusquedaArticulo {

    private final DlgBuscarArticulo dlgBuscarArticulo;
    private final ISeleccion seleccion;
    private final Component localizacion;

    private final ArticuloDAOImpl articuloDAO = DAOManager.getInstancia().getArticuloDAO();

    public ControladorBusquedaArticulo(Component localizacion, ISeleccion<Articulo> seleccion) {
        this.dlgBuscarArticulo = new DlgBuscarArticulo(null, true);
        this.seleccion = seleccion;
        this.localizacion = localizacion;
        init();
    }

    private void init() {
        dlgBuscarArticulo.txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarArticuloDescripcion();
            }
        });
        dlgBuscarArticulo.btnCancelar.addActionListener((e) -> {
            dlgBuscarArticulo.dispose();
        });
        dlgBuscarArticulo.btnSeleccionar.addActionListener((e) -> {
            seleccionarArticuloDlgBusqueda();
        });
    }

    //llenar la tabla de articulos del dialogo de busqueda a partir de lista
    private void llenaTablaArticulosDlgBusqueda(List<Articulo> articulos, JTable tabla) {
        TablaUtil.llenarTablaConEntity(tabla,
                articulos, new String[]{
                    "cod1","descripcion", "precioVenta", "cantidad"
                }, 0);
    }

    /////////////metodos publicos///////////////////
    /**
     * Leventa el dialogo de busqueda
     */
    public void mostrarDialogoBuscarArticulo() {
        //llenar tabla articulos dialogo de busqueda con articulos activos
        dlgBuscarArticulo.txtBuscar.setText("");// limpiar campo de busqueda
        List<Articulo> articulos = articuloDAO.readByNameQuery("Articulo.findByEstado", "estado", 1);
        llenaTablaArticulosDlgBusqueda(articulos, dlgBuscarArticulo.tblArticulos);
        dlgBuscarArticulo.setLocationRelativeTo(localizacion);
        dlgBuscarArticulo.setVisible(true);
    }

    ////////////eventos//////////
    private void buscarArticuloDescripcion() {
        List<Articulo> articulos = FuncionesArticulos
                .buscarArticulosPorDescripcionEstado(
                        1, dlgBuscarArticulo.txtBuscar.getText().trim()
                );
        llenaTablaArticulosDlgBusqueda(articulos, dlgBuscarArticulo.tblArticulos);
    }

    private void seleccionarArticuloDlgBusqueda() {
        Articulo art = TablaUtil.getEntityFilaSeleccionada(
                dlgBuscarArticulo.tblArticulos, 0, Articulo.class
        );
        if (art != null) {
            dlgBuscarArticulo.dispose();
            seleccion.articuloSelecccionado(art);
        }
    }
}
