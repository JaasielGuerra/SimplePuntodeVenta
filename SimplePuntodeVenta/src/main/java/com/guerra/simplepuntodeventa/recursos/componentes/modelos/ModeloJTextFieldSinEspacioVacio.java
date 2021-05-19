/*
 * Evitar espacios en blanco en un JTextField
 */
package com.guerra.simplepuntodeventa.recursos.componentes.modelos;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jaasiel
 */
public class ModeloJTextFieldSinEspacioVacio extends KeyAdapter {

    @Override
    public void keyTyped(KeyEvent e) {
        char caracter = e.getKeyChar();
        if (caracter == ' ') {
            e.consume();
        }
    }

}
