package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.CuentaCliente;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class CuentaClienteDAOImpl extends DAOImpl<CuentaCliente, Integer> {

    public CuentaClienteDAOImpl(EntityManagerFactory emf) {
        super(CuentaCliente.class, emf);
    }

}