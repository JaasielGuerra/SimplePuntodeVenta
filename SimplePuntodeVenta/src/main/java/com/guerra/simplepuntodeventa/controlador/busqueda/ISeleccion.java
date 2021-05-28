/*
 * Interfaz con metodo unico que proporciona el entity seleccionado  de la 
 * tabla del dialogo de busqueda de un articulo/servicio  en su argumento
 */
package com.guerra.simplepuntodeventa.controlador.busqueda;

/**
 *
 * @author Jaasiel
 * @param <Entity>
 */
public interface ISeleccion<Entity> {

    /**
     * Proporciona la entidad seleccionada en su argumento
     *
     * @param seleccionado
     */
    public void articuloSelecccionado(Entity seleccionado);
}
