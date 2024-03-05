package org.food.api.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.food.model.Order;

public interface KitchenService {
    void sendToKitchen(Order order) throws JsonProcessingException;
}
