package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Categoria;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class CategoriaDAOImpl extends DAOImpl<Categoria, Integer> {

    public CategoriaDAOImpl(EntityManagerFactory emf) {
        super(Categoria.class, emf);
    }

}
