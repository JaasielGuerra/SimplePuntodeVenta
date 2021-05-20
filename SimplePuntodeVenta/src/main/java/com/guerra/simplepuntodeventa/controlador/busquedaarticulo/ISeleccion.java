/*
 * Interfaz con metodo unico que proporciona el articulo seleccionado  de la 
 * tabla del dialogo de busqueda de un articulo  en su argumento
 */
package com.guerra.simplepuntodeventa.controlador.busquedaarticulo;

import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;

/**
 *
 * @author Jaasiel
 */
public interface ISeleccion {

    /**
     * Proporciona el articulo seleccionado en su argumento
     *
     * @param seleccionado
     */
    public void articuloSelecccionado(Articulo seleccionado);
}
