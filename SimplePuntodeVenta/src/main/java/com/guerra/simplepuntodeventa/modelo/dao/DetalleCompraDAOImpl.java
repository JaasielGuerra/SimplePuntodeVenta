package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.DetalleCompra;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class DetalleCompraDAOImpl extends DAOImpl<DetalleCompra, Integer> {

    public DetalleCompraDAOImpl(EntityManagerFactory emf) {
        super(DetalleCompra.class, emf.createEntityManager());
    }

}