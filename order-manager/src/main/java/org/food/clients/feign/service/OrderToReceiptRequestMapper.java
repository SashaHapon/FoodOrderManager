package org.food.clients.feign.service;

import org.food.clients.feign.dto.ReceiptRequest;
import org.food.model.Meal;
import org.food.model.Order;

import java.util.ArrayList;
import java.util.List;

import org.food.clients.feign.dto.ReceiptRequest.MealItemRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderToReceiptRequestMapper {

    public ReceiptRequest map(Order order) {
        ReceiptRequest receiptRequest = new ReceiptRequest();

        receiptRequest.setOrderId(order.getId());
        receiptRequest.setOrderTotalCost(order.getOrderSum());
        receiptRequest.setItems(mapItems(order.getMeals()));
        return receiptRequest;
    }

    private List<MealItemRequest> mapItems(List<Meal> meals) {
        List<MealItemRequest> items = new ArrayList<>();

        for (Meal meal : meals) {
            MealItemRequest item = new MealItemRequest();
            item.setName(meal.getName());
            item.setCount(meal.getTime());
            item.setCost(meal.getPrice());
            items.add(item);
        }
        return items;
    }
}