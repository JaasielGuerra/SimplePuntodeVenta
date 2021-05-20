package com.guerra.simplepuntodeventa.controlador.inventario;

import com.guerra.simplepuntodeventa.recursos.utilerias.PanelUtil;
import com.guerra.simplepuntodeventa.vista.inventario.IfrmMenuInventario;
import com.guerra.simplepuntodeventa.vista.inventario.PanAjustarInventario;
import com.guerra.simplepuntodeventa.vista.inventario.PanListInventario;
import com.guerra.simplepuntodeventa.vista.inventario.PanMovimientos;
import com.guerra.simplepuntodeventa.vista.inventario.VistaInventarioBean;

/**
 *
 * @author Jaasiel
 */
public class MenuInventario {

    /////vistas/////////////
    private final IfrmMenuInventario panMenuInventario;
    private final PanAjustarInventario panAjustarInventario = new PanAjustarInventario();
    private final PanMovimientos panMovimientos = new PanMovimientos();
    private final PanListInventario panListInventario = new PanListInventario();

    ////////controladores///////////////
    private final ControladorInventario controladorInventario = new ControladorInventario(
            new VistaInventarioBean(panAjustarInventario, panListInventario, panMovimientos)
    );

    public MenuInventario(IfrmMenuInventario panMenuInventario) {
        this.panMenuInventario = panMenuInventario;
        initOyentes();
    }

    private void initOyentes() {
        panMenuInventario.btnConsultarInv.addActionListener((e) -> {
            consultarInventario();
        });
        panMenuInventario.btnAJustar.addActionListener((e) -> {
            ajustarInventario();
        });
        panMenuInventario.btnMovs.addActionListener((e) -> {
            verMovimientos();
        });
        IfrmMenuInventario.PROCESO.setVisible(false);
    }

    /////////////////eventos///////////////
    private void consultarInventario() {
        controladorInventario.consultarInventario();
    }

    private void ajustarInventario() {
        controladorInventario.resetFormularioAjustarInventario();
        PanelUtil.cambiarPanel(IfrmMenuInventario.PAN_CAMBIANTE, panAjustarInventario);
    }

    private void verMovimientos() {
        controladorInventario.consultarMovimientos();

    }

}
