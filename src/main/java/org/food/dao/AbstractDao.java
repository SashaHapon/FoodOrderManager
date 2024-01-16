package org.food.dao;


import lombok.RequiredArgsConstructor;
import org.food.api.repository.GenericDao;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import java.util.List;


@Component
@RequiredArgsConstructor
public abstract class AbstractDao<T> implements GenericDao<T> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    private final Class<T> entityClass;

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T findById(int id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<T> findAll(int id, int limit) {

        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName()
                + " e ORDER BY e." + id + " ASC", entityClass).setMaxResults(limit).getResultList();
    }

    public T findOrderByIdWithEntityGraph(Integer id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("order_entity_graph");
        return entityManager.find(entityClass, id);
    }
}