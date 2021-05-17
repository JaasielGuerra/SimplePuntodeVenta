package com.guerra.simplepuntodeventa.vista.compras;

/**
 *
 * @author Jaasiel
 */
public class VistaCompraBean {

    private PanNuevaCompra panNuevaCompra;
    private PanListCompras panListCompras;

    public VistaCompraBean(PanNuevaCompra panNuevaCompra, PanListCompras panListCompras) {
        this.panNuevaCompra = panNuevaCompra;
        this.panListCompras = panListCompras;
    }

    public PanNuevaCompra getPanNuevaCompra() {
        return panNuevaCompra;
    }

    public void setPanNuevaCompra(PanNuevaCompra panNuevaCompra) {
        this.panNuevaCompra = panNuevaCompra;
    }

    public PanListCompras getPanListCompras() {
        return panListCompras;
    }

    public void setPanListCompras(PanListCompras panListCompras) {
        this.panListCompras = panListCompras;
    }

}
