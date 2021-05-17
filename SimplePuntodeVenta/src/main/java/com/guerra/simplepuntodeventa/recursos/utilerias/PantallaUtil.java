/*
 * Utilerias para la pantalla
 */
package com.guerra.simplepuntodeventa.recursos.utilerias;

import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author Jaasiel
 */
public class PantallaUtil {
    
    //constantes para un  margen por defecto
    public static final int MARGEN_X_PEQUENIO = 20;
    public static final int MARGEN_Y_PEQUENIO = 20;
    public static final int MARGEN_X_MEDIO = 50;
    public static final int MARGEN_Y_MEDIO = 50;

    private PantallaUtil() {

    }

    /**
     * Devuelve las dimensiones la pantalla
     *
     * @return un Dimencion
     */
    public static Dimension getDimensionesPantalla() {
        return java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * devuelve la anchura de la pantalla
     *
     * @return entero de la anchura
     */
    public static int getAnchuraPantalla() {
        return getDimensionesPantalla().width;
    }

    /**
     * Devuelve la altura de la pantalla
     *
     * @return un entero con la altura
     */
    public static int getAlturaPantalla() {
        return getDimensionesPantalla().height;
    }

    /**
     * Centra un jinternalFrame dado su contenedor padre y un margen
     *
     * @param iframe el componente a centrar
     * @param dsk el contenedor padre
     * @param mrg el margen deseado
     */
    public static void centrarIFRM(JInternalFrame iframe, JDesktopPane dsk, int mrg) {

        Dimension screenSize = dsk.getSize();
        Dimension d = new Dimension(screenSize.width - (mrg * 2), screenSize.height - (mrg * 2));
        iframe.setSize(d);

        int widthIfrm = iframe.getSize().width;
        int heightIfrm = iframe.getSize().height;
        iframe.setLocation(screenSize.width / 2 - widthIfrm / 2, screenSize.height / 2 - heightIfrm / 2);
    }

    /**
     * Centra un jinternalFrame dado su contenedor padre y un margen
     *
     * @param iframe el componente a centrar
     * @param dsk el contenedor padre
     * @param mrgX el margen en X
     * @param mrgY el margen en Y
     */
    public static void centrarIFRM(JInternalFrame iframe, JDesktopPane dsk, int mrgX, int mrgY) {

        Dimension screenSize = dsk.getSize();
        Dimension d = new Dimension(screenSize.width - (mrgX * 2), screenSize.height - (mrgY * 2));
        iframe.setSize(d);

        int widthIfrm = iframe.getSize().width;
        int heightIfrm = iframe.getSize().height;
        iframe.setLocation(screenSize.width / 2 - widthIfrm / 2, screenSize.height / 2 - heightIfrm / 2);
    }

}
