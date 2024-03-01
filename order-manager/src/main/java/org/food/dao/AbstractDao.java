package org.food.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.food.api.repository.GenericDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class AbstractDao<T> implements GenericDao<T> {

    private final Class<T> entityClass;
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);

        Root<T> root = criteriaQuery.from(entityClass);

        Order orderById = criteriaBuilder.asc(root.get("id"));
        criteriaQuery.orderBy(orderById);

        CriteriaQuery<T> selectQuery = criteriaQuery.select(root);

        TypedQuery<T> typedQuery = entityManager.createQuery(selectQuery);

        int firstResult = (id - 1) * limit;
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();

    }

    public T findOrderByIdWithEntityGraph(Integer id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("order_entity_graph");
        return entityManager.find(entityClass, id);
    }
}