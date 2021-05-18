package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.vista.articulos.IfrmMenuArticulos;
import com.guerra.simplepuntodeventa.vista.articulos.PanEditarArticulo;
import com.guerra.simplepuntodeventa.vista.articulos.PanListArticulos;
import com.guerra.simplepuntodeventa.vista.articulos.PanNuevoArticulo;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.PanCategorias;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.PanMarcas;

/**
 *
 * @author Jaasiel
 */
public class MenuArticulos {

    /////////vistas////////////////
    private final IfrmMenuArticulos panMenuArticulos;
    private final PanNuevoArticulo panNuevoArticulo = new PanNuevoArticulo();
    private final PanEditarArticulo panEditarArticulo = new PanEditarArticulo();
    private final PanListArticulos panListArticulos = new PanListArticulos();
    private final PanMarcas panMarcas = new PanMarcas();
    private final PanCategorias panCategorias = new PanCategorias();

    ////////////controladores///////////
    private final ControladorMarcas marcasAction = new ControladorMarcas(panMarcas);
    private final ControladorCategorias controladorCategorias = new ControladorCategorias(panCategorias);

    public MenuArticulos(IfrmMenuArticulos panMenuArticulos) {
        this.panMenuArticulos = panMenuArticulos;
        initOyentes();
    }

    private void initOyentes() {
        panMenuArticulos.btnNuevoArt.addActionListener((e) -> {
            nuevoArticulo();
        });
        panMenuArticulos.btnArticulos.addActionListener((e) -> {
            consultarArticulos();
        });
        panMenuArticulos.btnMarcas.addActionListener((e) -> {
            marcas();
        });
        panMenuArticulos.btnCategorias.addActionListener((e) -> {
            categorias();
        });
    }

    ////////////eventos//////////////
    private void nuevoArticulo() {
    }

    private void consultarArticulos() {
    }

    private void marcas() {
        marcasAction.resetFormulario();
        marcasAction.consultarMarcas();
        PanelUtil.cambiarPanel(IfrmMenuArticulos.PANEL_CAMBIANTE, panMarcas);
    }

    private void categorias() {
        controladorCategorias.resetFormulario();
        controladorCategorias.consultarCategorias();
        PanelUtil.cambiarPanel(IfrmMenuArticulos.PANEL_CAMBIANTE, panCategorias);
    }

}
