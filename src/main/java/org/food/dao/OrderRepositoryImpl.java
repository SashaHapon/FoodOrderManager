package org.food.dao;

import org.food.api.repository.OrderRepository;
import org.food.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends AbstractDao<Order> implements OrderRepository {

    public OrderRepositoryImpl() {
        super(Order.class);
    }
}
