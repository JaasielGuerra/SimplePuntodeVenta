package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Cliente;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class ClienteDAOImpl extends DAOImpl<Cliente, Integer> {

    public ClienteDAOImpl(EntityManagerFactory emf) {
        super(Cliente.class, emf.createEntityManager());
    }

}
