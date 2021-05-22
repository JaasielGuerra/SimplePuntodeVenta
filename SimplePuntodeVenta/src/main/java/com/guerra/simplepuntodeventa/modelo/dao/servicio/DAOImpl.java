package com.guerra.simplepuntodeventa.modelo.dao.servicio;

import com.guerra.simplepuntodeventa.modelo.Parameter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class DAOImpl<Entity, Id> extends CRUDImpl<Entity, Id> implements DAO<Entity, Id>, Serializable {

    private final Class<Entity> type;
    private final EntityManagerFactory emf;

    public DAOImpl(Class<Entity> t, EntityManagerFactory emf) {
        super(t, emf);
        type = t;
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Entity readOne(Id id) {
        EntityManager em = emf.createEntityManager();
        Entity find = em.find(type, id);
        return find;
    }

    @Override
    public List<Entity> readByNameQuery(String nameQuery, String p, Object v) {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Entity> query = em.createNamedQuery(nameQuery, type);

        query.setParameter(p, v);

        List<Entity> resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<Entity> readByQuery(String jpqlQuery, List<Parameter> parameters) {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Entity> query2 = em.createQuery(jpqlQuery, type);

        if (parameters != null) {

            parameters.forEach((parameter) -> {
                query2.setParameter(parameter.getParam(), parameter.getValue());
            });
        }

        List<Entity> resultList = query2.getResultList();

        return resultList;
    }

    @Override
    public List<Entity> readByQueryAndLimit(String jpqlQuery, List<Parameter> parameters, int startPosition, int maxResult) {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Entity> query2 = em.createQuery(jpqlQuery, type)
                .setFirstResult(startPosition).setMaxResults(maxResult);

        parameters.forEach((parameter) -> {
            query2.setParameter(parameter.getParam(), parameter.getValue());
        });

        List<Entity> resultList = query2.getResultList();

        return resultList;
    }

    @Override
    public int count(String jpqlQuery, List<Parameter> parameters) {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Object> query2 = em.createQuery(jpqlQuery, Object.class);

        parameters.forEach((parameter) -> {
            query2.setParameter(parameter.getParam(), parameter.getValue());
        });

        Object o = query2.getSingleResult();

        return Integer.valueOf(o.toString());
    }

    @Override
    public List<Object[]> readByQueryObjectCol(String jpqlQuery, List<Parameter> parameters) {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Object[]> query2 = em.createQuery(jpqlQuery, Object[].class);

        parameters.forEach((parameter) -> {
            query2.setParameter(parameter.getParam(), parameter.getValue());
        });

        List<Object[]> resultList = query2.getResultList();

        return resultList;
    }

    @Override
    public List<Entity> readByQuery(String jpqlQuery) {
        return readByQuery(jpqlQuery, null);
    }

}
