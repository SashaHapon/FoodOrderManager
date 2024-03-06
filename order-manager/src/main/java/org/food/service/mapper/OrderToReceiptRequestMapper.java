package org.food.service.mapper;

import org.food.clients.feign.dto.ReceiptRequest;
import org.food.clients.feign.dto.ReceiptRequest.Item;
import org.food.model.Meal;
import org.food.model.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderToReceiptRequestMapper {

    public ReceiptRequest map(Order order) {
        ReceiptRequest receiptRequest = new ReceiptRequest();

        receiptRequest.setOrderId(order.getId());
        receiptRequest.setOrderTotalCost(order.getOrderSum());
        receiptRequest.setItems(mapItems(order.getMeals()));
        return receiptRequest;
    }

    private List<Item> mapItems(List<Meal> meals) {
        List<ReceiptRequest.Item> items = new ArrayList<>();

        for (Meal meal : meals) {
            Item item = new Item();
            item.setName(meal.getName());
            item.setCount(meal.getTime());
            item.setCost(meal.getPrice());
            items.add(item);
        }
        return items;
    }
}