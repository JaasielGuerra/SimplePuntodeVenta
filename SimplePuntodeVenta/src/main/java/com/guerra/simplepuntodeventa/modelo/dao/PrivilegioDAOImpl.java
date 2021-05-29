package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Privilegio;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class PrivilegioDAOImpl extends DAOImpl<Privilegio, Integer> {

    public PrivilegioDAOImpl(EntityManagerFactory emf) {
        super(Privilegio.class, emf);
    }

}
