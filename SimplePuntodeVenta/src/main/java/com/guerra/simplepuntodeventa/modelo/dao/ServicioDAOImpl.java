package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Servicio;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class ServicioDAOImpl extends DAOImpl<Servicio, Integer> {

    public ServicioDAOImpl(EntityManagerFactory emf) {
        super(Servicio.class, emf);
    }

}
