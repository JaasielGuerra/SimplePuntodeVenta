/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.CuentaProveedor;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jaasiel
 */
public class CuentaProveedorDAOImpl extends DAOImpl<CuentaProveedor, Integer> {

    public CuentaProveedorDAOImpl(EntityManagerFactory emf) {
        super(CuentaProveedor.class, emf);
    }

}
