package org.food.api.service;

import org.food.dto.MealDto;
import org.food.dto.OrderDto;

import java.util.List;

public interface OrderService {

    void createOrder(Integer accountId, List<MealDto> mealDtoList);

    OrderDto getOrder(Integer accountId);

    void addMeals(Integer orderId, Integer[] mealsId);

    void removeMeals(Integer orderId, Integer[] mealsId);

    List<MealDto> getAllMeals(Integer orderId);
}
