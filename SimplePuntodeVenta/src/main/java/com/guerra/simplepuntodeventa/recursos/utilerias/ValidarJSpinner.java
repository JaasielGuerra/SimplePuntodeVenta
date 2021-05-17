package com.guerra.simplepuntodeventa.recursos.utilerias;

import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.UIManager;

/**
 *
 * @author Jaasiel
 */
public class ValidarJSpinner {

    private static final Color defaultColor = UIManager.getColor("JSpinner.background");
    private static final Color failedColor = Color.pink;

    private ValidarJSpinner() {

    }

    /**
     * Reiniciar valores integer y color de los JSPinner
     *
     * @param campos
     * @param valorDefecto setea este valor a los JSPinner
     */
    public static void resetValuesInteger(JSpinner[] campos, int valorDefecto) {
        for (JSpinner campo : campos) {
            campo.getEditor().setBackground(defaultColor);
            campo.setValue(valorDefecto);
        }
    }

    /**
     * Reiniciar valores double y color de los JSpinner
     *
     * @param campos
     * @param valorDefecto setea este valor a los JSPinner
     */
    public static void resetValuesDouble(JSpinner[] campos, double valorDefecto) {
        for (JSpinner campo : campos) {
            resetValuesDouble(campo, valorDefecto);
        }
    }

    /**
     * Reiniciar valor double y color del JSpinner
     *
     * @param campo
     * @param valorDefecto setea este valor a los JSPinner
     */
    public static void resetValuesDouble(JSpinner campo, double valorDefecto) {
        campo.getEditor().setBackground(defaultColor);
        campo.setValue(valorDefecto);
    }

    /**
     * Reiniciar color de los campos
     *
     * @param campos
     */
    public static void resetColor(JSpinner[] campos) {
        for (JSpinner campo : campos) {
            campo.getEditor().setBackground(defaultColor);
        }
    }

    /**
     * validar el valor minimo de un JSpinner
     *
     * @param min el valor double minimo
     * @param spn el JSpinner a validar
     * @return devuelve true si el valor esta por encima o igual al minimo
     */
    public static boolean valorDoubleMinimo(double min, JSpinner spn) {
        double valor = (double) spn.getValue();
        if (valor < min) {
            spn.getEditor().setBackground(failedColor);
        } else {
            spn.getEditor().setBackground(defaultColor);
        }
        return valor >= min;
    }

    /**
     * Validar el valor maximo de un JSpinner
     *
     * @param max el valor maximo en double
     * @param spn el JSpinner a validar
     * @return devuelve true si valor esta por debajo o igual al maximo
     */
    public static boolean valorDoubleMaximo(double max, JSpinner spn) {
        double valor = (double) spn.getValue();
        if (valor > max) {
            spn.getEditor().setBackground(failedColor);
        } else {
            spn.getEditor().setBackground(defaultColor);
        }
        return valor <= max;
    }

    /**
     * Validar un rango double de un JSpinner
     *
     * @param min el valor double minimp
     * @param max el valor double maximo
     * @param spn el JSpinner a validar
     * @return devuelve tre si el valor esta dentro del rango indicado
     */
    public static boolean valorDoubleRango(double min, double max, JSpinner spn) {
        double valor = (double) spn.getValue();
        if (valor < min || valor > max) {
            spn.getEditor().setBackground(failedColor);
        } else {
            spn.getEditor().setBackground(defaultColor);
        }
        return valor >= min && valor <= max;
    }

    /**
     * Validar que el valor del JSpinner sea mayor a n
     *
     * @param n el valor arbitrario
     * @param spn el JPinner
     * @return devuelver true si el valor esta por encima de n
     */
    public static boolean valorDoubleMayorN(double n, JSpinner spn) {
        double valor = (double) spn.getValue();
        if (valor <= n) {
            spn.getEditor().setBackground(failedColor);
        } else {
            spn.getEditor().setBackground(defaultColor);
        }
        return valor > n;
    }

    /**
     * Validar que el valor del JSpinner sea mayor a n
     *
     * @param n el valor arbitrario
     * @param spn el JPinner
     * @return devuelver true si el valor esta por encima de n
     */
    public static boolean valorIntegerMayorN(int n, JSpinner spn) {
        int valor = (int) spn.getValue();
        if (valor <= n) {
            spn.getEditor().setBackground(failedColor);
        } else {
            spn.getEditor().setBackground(defaultColor);
        }
        return valor > n;
    }
}
