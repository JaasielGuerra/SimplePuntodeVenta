package com.guerra.simplepuntodeventa.modelo.dao;

import com.guerra.simplepuntodeventa.modelo.dao.servicio.DAOImpl;
import com.guerra.simplepuntodeventa.modelo.entidades.Empresa;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jaasiel Guerra
 */
public class EmpresaDAOImpl extends DAOImpl<Empresa, Integer> {

    public EmpresaDAOImpl(EntityManagerFactory emf) {
        super(Empresa.class, emf.createEntityManager());
    }

}
