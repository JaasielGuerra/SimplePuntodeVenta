/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Ubicacion;
import java.io.Serializable;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jaasi
 */
public class UbicacionDAOImpl extends DAOImpl<Ubicacion, Integer> implements Serializable {

    public UbicacionDAOImpl(EntityManagerFactory emf) {

        super(Ubicacion.class, emf.createEntityManager());

    }

}
