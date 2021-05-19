package com.guerra.simplepuntodeventa.controlador.compras;

import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;

/**
 *
 * @author Jaasiel
 */
public interface IArticuloGuardado {

    /**
     * Se ejecuta cada vez que un articulo se guarda en el dialogo de nuevo
     * articulo
     * @param a
     */
    public void guardadoExitoso(Articulo a);
}
