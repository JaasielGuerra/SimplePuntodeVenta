package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.HistorialAbonoProveedor;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class HistorialAbonoProveedorDAOImpl extends DAOImpl<HistorialAbonoProveedor, Integer> {

    public HistorialAbonoProveedorDAOImpl(EntityManagerFactory emf) {
        super(HistorialAbonoProveedor.class, emf);
    }

}
