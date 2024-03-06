package org.food.service.mapper;

import org.food.dto.OrderMessage;
import org.food.model.Meal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemMapper {
    public List<OrderMessage.Item> itemMapper(List<Meal> meals) {
        List<OrderMessage.Item> items = new ArrayList<>();

        for (Meal meal : meals) {
            OrderMessage.Item item = new OrderMessage.Item();
            item.setName(meal.getName());
            item.setCookingTime(meal.getTime());
            items.add(item);
        }
        return items;
    }
}
