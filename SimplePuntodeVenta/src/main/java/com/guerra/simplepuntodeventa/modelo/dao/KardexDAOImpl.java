package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Kardex;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class KardexDAOImpl extends DAOImpl<Kardex, Integer> {

    public KardexDAOImpl(EntityManagerFactory emf) {
        super(Kardex.class, emf);
    }

}
