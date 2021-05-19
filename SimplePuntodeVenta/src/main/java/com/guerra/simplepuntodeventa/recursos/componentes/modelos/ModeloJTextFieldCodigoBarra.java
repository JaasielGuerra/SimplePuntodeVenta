/*
 * Para los campos que son de codigo de barras
 */
package com.guerra.simplepuntodeventa.recursos.componentes.modelos;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Jaasiel
 */
public class ModeloJTextFieldCodigoBarra implements KeyListener {

    private final ModeloJTextFieldCaracterMayuscula caracterMayuscula = new ModeloJTextFieldCaracterMayuscula();
    private final ModeloJTextFieldSinEspacioVacio sinEspacioVacio = new ModeloJTextFieldSinEspacioVacio();

    @Override
    public void keyTyped(KeyEvent e) {
        sinEspacioVacio.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        caracterMayuscula.keyReleased(e);
    }

}
