/*
 * Validar JComboBox
 */
package com.guerra.simplepuntodeventa.recursos.utilerias;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.UIManager;

/**
 *
 * @author Jaasiel
 */
public class ValidarJComboBox {

    private static final Color defaultColor = UIManager.getColor("JComboBox.background");
    private static final Color failedColor = Color.red;

    private ValidarJComboBox() {

    }

    /**
     * Reinicia los combos a su seleccion y color por defecto
     *
     * @param campos
     * @param indexNoSeleccion el indice de por defecto
     */
    public static void resetValues(JComboBox[] campos, int indexNoSeleccion) {
        for (JComboBox campo : campos) {
            campo.setBackground(defaultColor);
            campo.setSelectedIndex(indexNoSeleccion);
        }
    }

    /**
     * Reiniciar color de los campos
     *
     * @param campos
     */
    public static void resetColor(JComboBox[] campos) {
        for (JComboBox campo : campos) {
            campo.setBackground(defaultColor);
        }
    }

    /**
     * Valida que un combobox no este en el indice de no seleccion
     *
     * @param combo el JComboBox
     * @param indiceNoSeleccion es el indice que no puede seleccionarse
     * @return devuelve true si el combobox no esta en el indice de no seleccion
     */
    public static boolean seleccion(JComboBox combo, int indiceNoSeleccion) {
        boolean bandera = true;
        int indice = combo.getSelectedIndex();
        if (indice == indiceNoSeleccion) {
            bandera = false;
            combo.setBackground(failedColor);
        } else {
            combo.setBackground(defaultColor);
        }
        return bandera;
    }

    /**
     * Valida que los combos no esten en el indice de no seleccion
     *
     * @param combos un arreglo de combos
     * @param indiceNoSeleccion el indide de no seleccion de los combos
     * @return retorna true en caso que ningun combo este en el indice de no
     * seleccion
     */
    public static boolean seleccionCombos(JComboBox[] combos, int indiceNoSeleccion) {
        boolean bandera = true;
        for (JComboBox combo : combos) {
            if (!ValidarJComboBox.seleccion(combo, indiceNoSeleccion)) {
                bandera = false;
            }
        }
        return bandera;
    }
}
