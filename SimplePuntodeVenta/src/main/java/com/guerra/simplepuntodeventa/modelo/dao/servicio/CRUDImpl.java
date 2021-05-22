/**
 * Clase base que contiene los metodos necesarios para realizar un CRUD
 */
package com.guerra.simplepuntodeventa.modelo.dao.servicio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class CRUDImpl<Entity, Id> implements CRUD<Entity, Id>, Serializable {

    private final Class<Entity> type;
    private final EntityManagerFactory emf;

    public CRUDImpl(Class<Entity> t, EntityManagerFactory emf) {
        type = t;
        this.emf = emf;
    }

    @Override
    public void create(Entity entity) throws Exception {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.persist(entity);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {

        }
    }

    @Override
    public List<Entity> read() {
        EntityManager em = emf.createEntityManager();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(type));
        Query q = em.createQuery(cq);
        List resultList = q.getResultList();

        return resultList;
    }

    @Override
    public void update(Entity entity) throws Exception {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.merge(entity);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {

        }

    }

    @Override
    public void delete(Id id) throws Exception {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Entity entity;
            entity = em.getReference(type, id);
            if (entity == null) {
                throw new EntityNotFoundException();
            }

            em.remove(entity);
            em.getTransaction().commit();

        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {

        }
    }

}
