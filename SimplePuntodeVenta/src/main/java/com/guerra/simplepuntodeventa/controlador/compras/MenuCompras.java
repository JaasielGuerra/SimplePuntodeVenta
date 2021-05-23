package com.guerra.simplepuntodeventa.controlador.compras;

import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.vista.compras.IfrmMenuCompras;
import com.guerra.simplepuntodeventa.vista.compras.PanListCompras;
import com.guerra.simplepuntodeventa.vista.compras.PanNuevaCompra;
import com.guerra.simplepuntodeventa.vista.compras.VistaCompraBean;

/**
 *
 * @author Jaasiel
 */
public class MenuCompras {

    ////////////vistas////////
    private final IfrmMenuCompras panMenuCompra;
    private final PanNuevaCompra panNuevaCompra = new PanNuevaCompra();
    private final PanListCompras panListCompras = new PanListCompras();

    ////////////controladores//////////////
    private final ControladorCompra controladorCompra = new ControladorCompra(
            new VistaCompraBean(panNuevaCompra, panListCompras)
    );

    public MenuCompras(IfrmMenuCompras panMenuCompra) {
        this.panMenuCompra = panMenuCompra;

        initOyentes();
    }

    private void initOyentes() {
        panMenuCompra.btnConsultarCompras.addActionListener((e) -> {
            consultarCompras();
        });
        panMenuCompra.btnNuevaCompra.addActionListener((e) -> {
            nuevaCompra();
        });
        IfrmMenuCompras.PROCESO.setVisible(false);
    }

    /////////////////eventos///////
    private void consultarCompras() {
        controladorCompra.consultarCompras();

    }

    private void nuevaCompra() {
        controladorCompra.resetFormularioCompra();
        PanelUtil.cambiarPanel(IfrmMenuCompras.PANEL_CAMBIANTE, panNuevaCompra);
    }

}
