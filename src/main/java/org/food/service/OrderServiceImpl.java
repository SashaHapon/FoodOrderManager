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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;

    private final OrderRepository orderRepository;

    private final AccountRepository accountRepository;

    private final MealRepository mealRepository;

    @Override
    public OrderDto createOrder(Integer accountId, List<MealDto> mealDtoList) {

        Order order = new Order();
        order.setAccount(accountRepository.findById(accountId));
        Type listType = new TypeToken<List<Meal>>() {
        }.getType();
        List<Meal> meals = modelMapper.map(mealDtoList, listType);

        orderPriceSum(meals);
        order.setMeals(meals);
        order.setOrderSum(orderPriceSum(meals));
        order.setCookingTimeSum(cookingTimeSum(meals));
        Order orderr = orderRepository.create(order);
        return modelMapper.map(orderr, OrderDto.class);
    }

    @Override
    public OrderDto getOrder(Integer id) {

        return modelMapper.map(orderRepository.findById(id), OrderDto.class);
    }

    @Override
    public void addMeals(Integer orderId, List<MealDto> mealDtos) {

        Order order = orderRepository.findById(orderId);
        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();
        List<Meal> mealList = modelMapper.map(mealDtos, listType);

        order.setOrderSum(orderPriceSum(mealList));
        order.setMeals(mealList);
        order.setCookingTimeSum(cookingTimeSum(mealList));
        orderRepository.update(order);
    }

    @Override
    public OrderDto removeMeals(Integer orderId, List<MealDto> mealDtosToRemove) {

        Order order = orderRepository.findById(orderId);
        List<Meal> orderMeals = order.getMeals();

        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();
        List<Meal> mealListToRemove = modelMapper.map(mealDtosToRemove, listType);

        List<Meal> filteredMeals = orderMeals.stream()
                .filter(orderMeal -> mealListToRemove.stream()
                        .noneMatch(mealToRemove -> mealToRemove.getName().equals(orderMeal.getName())))
                .collect(Collectors.toList());

        order.setMeals(filteredMeals);
        order.setOrderSum(orderPriceSum(filteredMeals));
        order.setCookingTimeSum(cookingTimeSum(filteredMeals));
        OrderDto orderDto = modelMapper.map(orderRepository.update(order), OrderDto.class);
        return orderDto;
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

        BigDecimal price = new BigDecimal(0);

        for (Meal meal : mealList) {
            price.add(meal.getPrice());
        }
        return price;
    }
}
