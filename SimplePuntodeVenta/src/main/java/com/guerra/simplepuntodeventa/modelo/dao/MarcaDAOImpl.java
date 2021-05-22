package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Marca;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class MarcaDAOImpl extends DAOImpl<Marca, Integer> {

    public MarcaDAOImpl(EntityManagerFactory emf) {
        super(Marca.class, emf);
    }

}
