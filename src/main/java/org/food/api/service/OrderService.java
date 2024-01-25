package org.food.api.service;

import org.food.dto.MealDto;
import org.food.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(Integer accountId, List<MealDto> mealDtoList);

    OrderDto getOrder(Integer accountId);

    void addMeals(Integer orderId,  List<MealDto> mealDtos);

    OrderDto removeMeals(Integer orderId, List<MealDto> mealDtosToRemove);

    List<MealDto> getAllMeals(Integer orderId);
}
