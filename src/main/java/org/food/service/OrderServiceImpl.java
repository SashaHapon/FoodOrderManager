package org.food.service;

import lombok.RequiredArgsConstructor;
import org.food.api.repository.AccountRepository;
import org.food.api.repository.MealRepository;
import org.food.api.repository.OrderRepository;
import org.food.api.service.OrderService;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.food.model.Meal;
import org.food.model.Order;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;

    private final OrderRepository orderRepository;

    private final AccountRepository accountRepository;

    private final MealRepository mealRepository;

    @Override
    public void createOrder(Integer accountId, List<MealDto> mealDtoList) {

        Order order = new Order();
        order.setAccount(accountRepository.findById(accountId));
        Type listType = new TypeToken<List<Meal>>() {
        }.getType();
        List<Meal> meals = modelMapper.map(mealDtoList, listType);

        orderPriceSum(meals);
        order.setMeals(meals);
        order.setOrderSum(orderPriceSum(meals));
        order.setCookingTimeSum(cookingTimeSum(meals));
        orderRepository.create(order);
    }

    @Override
    public OrderDto getOrder(Integer id) {

        return modelMapper.map(orderRepository.findById(id), OrderDto.class);
    }

    @Override
    public void addMeals(Integer orderId, Integer[] mealsId) {

        Order order = orderRepository.findById(orderId);
        List<Meal> meals = orderRepository.findById(orderId).getMeals();

        for (int mealId : mealsId) {
            meals.add(mealRepository.findById(mealId));
        }

        order.setOrderSum(orderPriceSum(meals));
        order.setMeals(meals);
        order.setCookingTimeSum(cookingTimeSum(meals));
        orderRepository.update(order);
    }

    @Override
    public void removeMeals(Integer orderId, Integer[] mealsId) {

        Order order = orderRepository.findById(orderId);
        List<Meal> meals = orderRepository.findById(orderId).getMeals();

        for (int mealId : mealsId) {
            meals.remove(mealRepository.findById(mealId));
        }

        order.setMeals(meals);
        order.setOrderSum(orderPriceSum(meals));
        order.setCookingTimeSum(cookingTimeSum(meals));
        orderRepository.update(order);
    }

    @Override
    public List<MealDto> getAllMeals(Integer orderId) {
        Order order = orderRepository.findOrderByIdWithEntityGraph(orderId);
        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();
        return modelMapper.map(order.getMeals(), listType);
    }

    private int cookingTimeSum(List<Meal> mealList) {

        int cookingTimeSum = 0;
        for (Meal meal : mealList) {
            cookingTimeSum += meal.getTime();
        }
        return cookingTimeSum;
    }

    private BigDecimal orderPriceSum(List<Meal> mealList) {

        BigDecimal price = null;
        for (Meal meal : mealList) {
            price = price.add(meal.getPrice());
        }
        return price;
    }
}
