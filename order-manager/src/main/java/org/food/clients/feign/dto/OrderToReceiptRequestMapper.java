package org.food.clients.feign.dto;

import org.food.model.Meal;
import org.food.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderToReceiptRequestMapper {

    public static ReceiptRequest map(Order order) {
        ReceiptRequest receiptRequest = new ReceiptRequest();

        receiptRequest.setOrderId(order.getId());
        receiptRequest.setOrderTotalCost(order.getOrderSum());
        receiptRequest.setItems(mapItems(order.getMeals()));
        return receiptRequest;
    }

    private static List<ReceiptRequest.Item> mapItems(List<Meal> meals) {
        List<ReceiptRequest.Item> items = new ArrayList<>();

        for (Meal meal : meals) {
            ReceiptRequest.Item item = new ReceiptRequest.Item();
            item.setName(meal.getName());
            item.setCount(meal.getTime());
            item.setCost(meal.getPrice());
            items.add(item);
        }
        return items;
    }
}