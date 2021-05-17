package com.guerra.simplepuntodeventa.vista.inventario;

/**
 *
 * @author Jaasiel
 */
public class VistaInventarioBean {

    private PanAjustarInventario panAjustarInventario;
    private PanListInventario panListInventario;
    private PanMovimientos panMovimientos;

    public VistaInventarioBean(PanAjustarInventario panAjustarInventario, PanListInventario panListInventario, PanMovimientos panMovimientos) {
        this.panAjustarInventario = panAjustarInventario;
        this.panListInventario = panListInventario;
        this.panMovimientos = panMovimientos;
    }

    public PanAjustarInventario getPanAjustarInventario() {
        return panAjustarInventario;
    }

    public void setPanAjustarInventario(PanAjustarInventario panAjustarInventario) {
        this.panAjustarInventario = panAjustarInventario;
    }

    public PanListInventario getPanListInventario() {
        return panListInventario;
    }

    public void setPanListInventario(PanListInventario panListInventario) {
        this.panListInventario = panListInventario;
    }

    public PanMovimientos getPanMovimientos() {
        return panMovimientos;
    }

    public void setPanMovimientos(PanMovimientos panMovimientos) {
        this.panMovimientos = panMovimientos;
    }

}
