/*
 * Modelo para mandar parametros en la utileria de ServicioImpresionUtil
 */
package com.guerra.simplepuntodeventa.recursos.utilerias.modelo;

/**
 *
 * @author jaasi
 */
public class ParametroReporte {

    public static final int OBJETO = 1;
    public static final int NOMBRE_SUB_RPT = 2;

    private Object valor;
    private String nombre;
    private int tipo;

    public ParametroReporte(String nombre, Object valor, int tipo) {
        this.valor = valor;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
