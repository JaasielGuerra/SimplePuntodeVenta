/*
 * Contiene datos globales para usar en cualquier parte de la aplicacion
 */
package com.guerra.simplepuntodeventa.global;

import java.util.HashMap;

/**
 *
 * @author Jaasiel
 */
public class Session {

    private static HashMap<String, Object> sessionMap;
    private static Session session;

    private Session() {
        sessionMap = new HashMap<>();
    }

    /**
     * Obtener una instancia global de la sesion
     * @return la sesion global
     */
    public static Session getInstancia() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    /**
     * Agregar un atributo a la sesion
     * @param k la clave
     * @param v el valor
     */
    public void setAttribute(String k, Object v) {
        sessionMap.put(k, v);
    }

    /**
     * Devuelve un atributo indicado en su parametro
     * @param k el atributo requerido
     * @return el atributo
     */
    public Object getAttribute(String k) {
        return sessionMap.get(k);
    }
}
