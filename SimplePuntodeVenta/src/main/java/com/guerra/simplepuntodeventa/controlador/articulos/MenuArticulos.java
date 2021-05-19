package com.guerra.simplepuntodeventa.controlador.articulos;

import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.vista.articulos.IfrmMenuArticulos;
import com.guerra.simplepuntodeventa.vista.articulos.PanEditarArticulo;
import com.guerra.simplepuntodeventa.vista.articulos.PanListArticulos;
import com.guerra.simplepuntodeventa.vista.articulos.PanNuevoArticulo;
import com.guerra.simplepuntodeventa.vista.articulos.VistaArticuloBean;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.PanCategorias;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.PanMarcas;
import com.guerra.simplepuntodeventa.vista.articulos.caracteristicas.PanUbicaciones;

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
    private final PanUbicaciones panUbicaciones = new PanUbicaciones();

    ////////////controladores///////////
    private final ControladorArticulos articulosAction = new ControladorArticulos(
            new VistaArticuloBean(panNuevoArticulo, panEditarArticulo, panListArticulos)
    );
    private final ControladorMarcas marcasAction = new ControladorMarcas(panMarcas);
    private final ControladorCategorias controladorCategorias = new ControladorCategorias(panCategorias);
    private final ControladorUbicaciones controladorUbicaciones = new ControladorUbicaciones(panUbicaciones);

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
        panMenuArticulos.btnUbicaciones.addActionListener((ae) -> {
            ubicaciones();
        });
    }

    ////////////eventos//////////////
    private void nuevoArticulo() {
        articulosAction.cargarCombosCaracteristicasNuevoArticulo();
        articulosAction.resetValuesNuevoArticulo();
        PanelUtil.cambiarPanel(IfrmMenuArticulos.PANEL_CAMBIANTE, panNuevoArticulo);
    }

    private void consultarArticulos() {
        articulosAction.consultarArticulos();
        PanelUtil.cambiarPanel(IfrmMenuArticulos.PANEL_CAMBIANTE, panListArticulos);
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

    private void ubicaciones() {
        controladorUbicaciones.resetFormulario();
        controladorUbicaciones.consultarUbicaciones();
        PanelUtil.cambiarPanel(IfrmMenuArticulos.PANEL_CAMBIANTE, panUbicaciones);
    }

}
