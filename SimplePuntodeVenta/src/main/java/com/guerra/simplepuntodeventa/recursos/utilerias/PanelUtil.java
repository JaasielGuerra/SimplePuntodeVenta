/*
 * Clase encargada para cambiar de paneles
 */
package com.guerra.simplepuntodeventa.recursos.utilerias;

import javax.swing.JPanel;

public class PanelUtil {

    //Constructor 
    private PanelUtil() {

    }

    /**
     * Cambiar panel
     *
     * @param PnlContenedor el panel de destino
     * @param PnlInsertar el panel a colocar en el destino
     */
    public static void cambiarPanel(JPanel PnlContenedor, JPanel PnlInsertar) {
        PnlContenedor.removeAll();
        PnlContenedor.revalidate();
        PnlContenedor.repaint();
        PnlContenedor.add(PnlInsertar);
        PnlContenedor.revalidate();
        PnlContenedor.repaint();
    }

}
