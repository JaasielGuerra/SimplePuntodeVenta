/*
 * Convierte los caracteres de un campo de texto a mayuscula
 */
package com.guerra.simplepuntodeventa.recursos.componentes.modelos;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Jaasiel
 */
public class ModeloJTextFieldCaracterMayuscula extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {
        JTextField txt = (JTextField) e.getSource();
        txt.setText(txt.getText().toUpperCase());
    }
}
