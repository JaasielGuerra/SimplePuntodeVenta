package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.HistorialAbonoCliente;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class HistorialAbonoClienteDAOImpl extends DAOImpl<HistorialAbonoCliente, Integer> {

    public HistorialAbonoClienteDAOImpl(EntityManagerFactory emf) {
        super(HistorialAbonoCliente.class, emf.createEntityManager());
    }

}
