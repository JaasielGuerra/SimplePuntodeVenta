package com.guerra.simplepuntodeventa.vista.articulos;

/**
 *
 * @author Jaasiel
 */
public class VistaArticuloBean {

    private PanNuevoArticulo panNuevoArticulo;
    private PanEditarArticulo panEditarArticulo;
    private PanListArticulos panListArticulos;

    public VistaArticuloBean(PanNuevoArticulo panNuevoArticulo, PanEditarArticulo panEditarArticulo, PanListArticulos panListArticulos) {
        this.panNuevoArticulo = panNuevoArticulo;
        this.panEditarArticulo = panEditarArticulo;
        this.panListArticulos = panListArticulos;
    }

    public PanNuevoArticulo getPanNuevoArticulo() {
        return panNuevoArticulo;
    }

    public void setPanNuevoArticulo(PanNuevoArticulo panNuevoArticulo) {
        this.panNuevoArticulo = panNuevoArticulo;
    }

    public PanEditarArticulo getPanEditarArticulo() {
        return panEditarArticulo;
    }

    public void setPanEditarArticulo(PanEditarArticulo panEditarArticulo) {
        this.panEditarArticulo = panEditarArticulo;
    }

    public PanListArticulos getPanListArticulos() {
        return panListArticulos;
    }

    public void setPanListArticulos(PanListArticulos panListArticulos) {
        this.panListArticulos = panListArticulos;
    }

}
