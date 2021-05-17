/*
 * Esta clase se encarga de crear la entidad de persistencia
 * para reutilzarla las veces que se necesite
 */
package com.guerra.simplepuntodeventa.modelo;

import java.io.File;
import java.sql.Connection;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jaasiel Guerra
 */
public class EMFactory {

    // atributos estaticos
    private static final String PERSISTENCE_UNIT_NAME = "persistence_unit";
    private static EntityManagerFactory factory;

    //evitar la instancia con operador new
    private EMFactory() {

    }

    /**
     * Devuelve la ruta en donde esta la base de datos
     *
     * @return
     */
    public static String getRutaDB() {
        String rutaApp = System.getProperty("user.dir")
                + File.separator + "data" + File.separator + "db_spv.db";
        return rutaApp;
    }

    /**
     * Devuelve el EntityManagerFactory
     *
     * @return
     */
    public static EntityManagerFactory getEMFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    /**
     * Devuelve la conexion a la base de datos a la que se conecta con las
     * propiedades del arhivo de persistencia de JPA
     *
     * @return
     */
    public static Connection getConnectionMysql() {
        Connection con = null;
        con = new JPAConnectionFactory().getConnection("mysql", factory.getProperties());
        return con;
    }

}
