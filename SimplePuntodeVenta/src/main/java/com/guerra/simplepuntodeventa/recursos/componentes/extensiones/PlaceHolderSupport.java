package com.guerra.simplepuntodeventa.recursos.componentes.extensiones;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class PlaceHolderSupport {

    private PlaceHolderSupport() {

    }

    /**
     * Agregar placeholder a un JTextField
     *
     * @param text
     * @param field
     */
    public static void setPlaceHolder(String text, JTextField field) {

        TextPrompt textPrompt = new TextPrompt(text, field);
        textPrompt.changeAlpha(0.5f);
    }

    /**
     * Agregar un placeholder con imagen a un JTextField
     *
     * @param text
     * @param img
     * @param field
     */
    public static void setPlaceHolder(String text, String img, JTextField field) {

        TextPrompt textPrompt = new TextPrompt(text, field);
        textPrompt.changeAlpha(0.5f);
        textPrompt.setIcon(new ImageIcon(PlaceHolderSupport.class.getResource("/img/" + img + ".png")));

    }
}
