package com.guerra.simplepuntodeventa.recursos.utilerias;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Jaasiel
 */
public class FechaUtil {

    private FechaUtil() {

    }

    /**
     * Devuelve una fecha en String al formato yyyy-MM-dd (ISO8601) para sqlite3
     *
     * @param fecha
     * @return
     */
    public static String formatearFechaTextoSqlite3(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String txtFecha = formato.format(fecha);
        return txtFecha;
    }
}
