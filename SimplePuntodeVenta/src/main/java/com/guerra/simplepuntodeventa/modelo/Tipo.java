/*
 * Modelo para los combos de tipo de una venta o compra
 */
package com.guerra.simplepuntodeventa.modelo;

/**
 *
 * @author Jaasiel
 */
public class Tipo {

    private String nombre;
    private int valor;

    public Tipo(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public Tipo() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
