package org.food.api.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface GenericDao<T> {

    T create(T entity);

    T findById(int id);

    T update(T entity);

    void delete(T entity);

    List<T> findAll(int id, int limit);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "order_entity_graph")
    T findOrderByIdWithEntityGraph(Integer id);
}