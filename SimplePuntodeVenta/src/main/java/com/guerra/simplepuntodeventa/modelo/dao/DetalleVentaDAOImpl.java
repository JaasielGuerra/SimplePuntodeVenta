package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.DetalleVenta;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class DetalleVentaDAOImpl extends DAOImpl<DetalleVenta, Integer> {

    public DetalleVentaDAOImpl(EntityManagerFactory emf) {
        super(DetalleVenta.class, emf.createEntityManager());
    }

}
