package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Venta;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class VentaDAOImpl extends DAOImpl<Venta, Integer> {

    public VentaDAOImpl(EntityManagerFactory emf) {
        super(Venta.class, emf.createEntityManager());
    }

}
