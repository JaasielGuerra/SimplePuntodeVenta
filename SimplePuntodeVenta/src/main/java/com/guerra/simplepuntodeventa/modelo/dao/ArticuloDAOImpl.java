package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Articulo;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class ArticuloDAOImpl extends DAOImpl<Articulo, Integer> {

    public ArticuloDAOImpl(EntityManagerFactory emf) {
        super(Articulo.class, emf.createEntityManager());
    }

}
