/*
 * Crear objetos Connection para distintas bases de datos, usando las
 * propiedades de conexión del archivo de persistencia de JPA
 */
package com.guerra.simplepuntodeventa.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author jaasiel
 */
public class JPAConnectionFactory {

    /**
     * Retornar una conexión a una base de datos
     *
     * @param motor
     * @param propiedades
     * @return
     */
    public Connection getConnection(String motor, Map propiedades) {

        if (propiedades == null) {
            return null;
        }

        Connection con = null;

        String url = propiedades.get("javax.persistence.jdbc.url").toString();
        String user = propiedades.get("javax.persistence.jdbc.user").toString();
        String pass = propiedades.get("javax.persistence.jdbc.password").toString();

        if (motor.equalsIgnoreCase("MYSQL")) {

            try {
                con = DriverManager.getConnection(url, user, pass);
            } catch (SQLException ex) {
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("\u001B[SQLException: " + ex.getMessage());
                System.out.println("\u001B[SQLState: " + ex.getSQLState());
                System.out.println("\u001B[VendorError: " + ex.getErrorCode());
                System.out.println("-----------------------------------------------------------------------");
            }

        } else if (motor.equalsIgnoreCase("SQLITE3")) {
            try {
                con = DriverManager.getConnection(url);
            } catch (SQLException ex) {
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("\u001B[SQLException: " + ex.getMessage());
                System.out.println("\u001B[SQLState: " + ex.getSQLState());
                System.out.println("\u001B[VendorError: " + ex.getErrorCode());
                System.out.println("-----------------------------------------------------------------------");
            }
        }

        return con;
    }

}
