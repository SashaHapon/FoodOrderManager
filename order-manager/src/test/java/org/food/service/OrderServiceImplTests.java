package org.food.service;

import org.food.api.repository.AccountRepository;
import org.food.api.repository.MealRepository;
import org.food.api.repository.OrderRepository;
import org.food.api.service.KitchenService;
import org.food.dto.AccountDto;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.food.exception.classes.NotFoundException;
import org.food.model.Account;
import org.food.model.Meal;
import org.food.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTests {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MealRepository mealRepository;
    @Mock
    private KitchenService kitchenService;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Return order after create")
    public void should_returnOrder_whenTryToCreateOrder() {

        Account account = new Account();
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        List<Meal> mealList = new ArrayList<>();
        List<MealDto> mealDtoList = new ArrayList<>();
        OrderDto expectedOrderDtoOutput = new OrderDto();
        expectedOrderDtoOutput.setAccount(accountDto);
        Order returnedOrder = new Order();
        returnedOrder.setAccount(account);


        when(accountRepository.findById(1)).thenReturn(account);
        when(orderRepository.create(any(Order.class))).thenReturn(returnedOrder);
        doNothing().when(kitchenService).sendToKitchen(returnedOrder);
        when(modelMapper.map(returnedOrder, OrderDto.class)).thenReturn(expectedOrderDtoOutput);


        OrderDto returnedOrderDto = orderService.createOrder(1, mealDtoList);

        assertThat(expectedOrderDtoOutput).isEqualTo(returnedOrderDto);
    }

    @Test
    @DisplayName("Throw exception when try to create order")
    public void should_throwException_whenTryToCreateOrder() {

        Account account = new Account();

        Integer id = 1;
        List<Meal> mealList = new ArrayList<>(3);
        List<MealDto> mealDtoList = new ArrayList<>(3);

        when(accountRepository.findById(anyInt())).thenReturn(account);
        when(orderRepository.create(any(Order.class))).thenThrow(IllegalArgumentException.class);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderService.createOrder(id, mealDtoList));
    }

    @Test
    @DisplayName("Return order by id")
    public void should_returnOrder_when_TryToGetOrder() {
        int id = 1;
        OrderDto expectedOrderDtoOutput = new OrderDto();
        Order testOrder = new Order();

        when(orderRepository.findById(id)).thenReturn(testOrder);
        when(modelMapper.map(testOrder, OrderDto.class)).thenReturn(expectedOrderDtoOutput);

        OrderDto createdOrderDto = orderService.getOrder(id);

        assertThat(expectedOrderDtoOutput).isEqualTo(createdOrderDto);
    }

    @Test
    @DisplayName("Throw exception when try to get order by id")
    public void should_throwException_when_TryToGetOrder() {
        int id = 1;
        Order testOrder = null;

        when(orderRepository.findById(id)).thenReturn(testOrder).thenThrow(NotFoundException.class);
//        when(modelMapper.map(testOrder, OrderDto.class)).thenThrow(IllegalArgumentException.class);

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> orderService.getOrder(id));
    }

    @Test
    @DisplayName("Add meals to order")
    public void should_addMealsToOrder_when_tryToAddMeals() {

        Order testOrder = new Order();
        Meal meal = new Meal();
        meal.setPrice(new BigDecimal(12));
        meal.setTime(12);
        MealDto mealDto = new MealDto();
        List<Meal> meals = new ArrayList<>(List.of(meal));
        List<MealDto> mealDtos = new ArrayList<>(List.of(mealDto));
        Type listType = new TypeToken<List<Meal>>() {
        }.getType();
        testOrder.setMeals(meals);

        when(orderRepository.findById(1)).thenReturn(testOrder);
        when(modelMapper.map(mealDtos, listType)).thenReturn(meals);
        when(orderRepository.update(testOrder)).thenReturn(testOrder);

        orderService.addMeals(1, mealDtos);

        assertThat(testOrder.getMeals()).isEqualTo(meals);
    }

    @Test
    @DisplayName("Remove meals and and return order dto without them")
    public void should_removeMeals_and_returnOrderDtoWithoutThem() {

        int id = 1;
        Order testOrder = new Order();
        OrderDto orderDto = new OrderDto();
        MealDto mealDto = new MealDto(1, "name3", new BigDecimal(555), 5);

        Meal meal = new Meal(1, "name1", new BigDecimal(555), 5);
        Meal meal1 = new Meal(2, "name2", new BigDecimal(555), 5);
        Meal meal2 = new Meal(1, "name3", new BigDecimal(555), 5);

        List<Meal> mealsToDelete = new ArrayList<>(List.of(meal, meal1));
        List<Meal> orderMeals = new ArrayList<>(List.of(meal, meal1, meal2));
        List<MealDto> mealDtos = new ArrayList<>(List.of());
        Type listType = new TypeToken<List<Meal>>() {
        }.getType();
        testOrder.setMeals(orderMeals);
        orderDto.setMeals(List.of(mealDto));


        when(orderRepository.findById(id)).thenReturn(testOrder);
        when(modelMapper.map(mealDtos, listType)).thenReturn(mealsToDelete);
        when(orderRepository.update(testOrder)).thenReturn(testOrder);
        when(modelMapper.map(testOrder, OrderDto.class)).thenReturn(orderDto);

        OrderDto order = orderService.removeMeals(1, mealDtos);
        assertThat(orderDto.getMeals()).isEqualTo(List.of(mealDto));
    }

    @Test
    @DisplayName("Must be return all mealDtos")
    public void should_returnAllMealDtos_when_tryToGetAllMeals() {

        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();

        int orderId = 1;
        Order order = new Order();
        List<Meal> mockMeals = Arrays.asList(new Meal(), new Meal(), new Meal());
        order.setMeals(mockMeals);

        when(orderRepository.findById(orderId)).thenReturn(order);
        when(modelMapper.map(order.getMeals(), listType)).thenReturn(mockMeals);

        List<MealDto> returnedMealDtos = orderService.getAllMeals(orderId);
        assertThat(returnedMealDtos).isNotNull();
        assertEquals(returnedMealDtos, mockMeals);
    }
}
