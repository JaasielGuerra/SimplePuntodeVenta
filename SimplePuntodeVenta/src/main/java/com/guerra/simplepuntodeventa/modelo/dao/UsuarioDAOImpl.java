package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Usuario;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class UsuarioDAOImpl extends DAOImpl<Usuario, Integer> {

    public UsuarioDAOImpl(EntityManagerFactory emf) {
        super(Usuario.class, emf);
    }

}
