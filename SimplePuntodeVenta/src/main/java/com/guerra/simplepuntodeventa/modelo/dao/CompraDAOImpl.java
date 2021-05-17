package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Compra;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class CompraDAOImpl extends DAOImpl<Compra, Integer> {

    public CompraDAOImpl(EntityManagerFactory emf) {
        super(Compra.class, emf.createEntityManager());
    }

}
