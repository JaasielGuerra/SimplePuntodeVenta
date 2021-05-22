package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Proveedor;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class ProveedorDAOImpl extends DAOImpl<Proveedor, Integer> {

    public ProveedorDAOImpl(EntityManagerFactory emf) {
        super(Proveedor.class, emf);
    }

}
