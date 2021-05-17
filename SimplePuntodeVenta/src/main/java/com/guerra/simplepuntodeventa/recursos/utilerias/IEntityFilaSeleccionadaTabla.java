/*
 * Interfaz para la utileria de TablaUtil, se usa para pasar el Entity de 
 * la fila que esta seleccionada en un JTable y ejecutar alguna accion
 */
package com.guerra.simplepuntodeventa.recursos.utilerias;

/**
 *
 * @author jaasi
 */
public interface IEntityFilaSeleccionadaTabla<T> {

    public void accion(T entity);
}
