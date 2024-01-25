package org.food.service;

import org.food.api.repository.AccountRepository;
import org.food.api.repository.MealRepository;
import org.food.api.repository.OrderRepository;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.food.model.Meal;
import org.food.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MealRepository mealRepository;
    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(OrderServiceImplTest.class);
    }

    //@Test
    @DisplayName("should_returnOrder_whenTryToCreateOrder")
    public void should_returnOrder_whenTryToCreateOrder() {

        OrderDto orderDto = new OrderDto();
        Order mappedOrder = new Order();
        Order returnOrder = new Order();
        List<MealDto> mealDtoList = new ArrayList<>();
        OrderDto expectedOrderDtoOutput = new OrderDto();

        when(modelMapper.map(orderDto, Order.class)).thenReturn(mappedOrder);
        when(orderRepository.create(mappedOrder)).thenReturn(returnOrder);
        when(modelMapper.map(returnOrder, OrderDto.class)).thenReturn(expectedOrderDtoOutput);

        orderService.createOrder(1, mealDtoList);

//        assertThat(expectedOrderDtoOutput).isEqualTo(createdOrderDto);
    }

    @Test
    @DisplayName("returnOrder_when_getOrderById")
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
    @DisplayName("addMealsToOrder_when_AddMeals")
    public void should_addMealsToOrder_when_tryToAddMeals() {

        int id = 1;
        OrderDto expectedOrderDtoOutput = new OrderDto();
        Order testOrder = new Order();
        List<Meal> meals = new ArrayList<>();
        List<MealDto> mealDtos = new ArrayList<>();
        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();

        when(orderRepository.findById(id)).thenReturn(testOrder);
        when(modelMapper.map(mealDtos, listType)).thenReturn(meals);
        when(orderRepository.update(testOrder)).thenReturn(testOrder);

        orderService.addMeals(1, mealDtos);

        assertThat(testOrder.getMeals()).isEqualTo(meals);
    }

    @Test
    @DisplayName("removeMeals_and_returnOrderDtoWithoutThem")
    public void should_removeMeals_and_returnOrderDtoWithoutThem() {

        Integer id = 1;
        Order testOrder = new Order();
        OrderDto orderDto = new OrderDto();
        MealDto mealDto = new MealDto(1, "", new BigDecimal(123), 24);

        Meal meal = new Meal(1, "name1", new BigDecimal(555), 5, "");
        Meal meal1 = new Meal(2, "name2", new BigDecimal(555), 5, "");
        Meal meal2 = new Meal(1, "name3", new BigDecimal(555), 5, "");

        List<Meal> mealsToDelete = new ArrayList<>(List.of(meal, meal1));
        List<Meal> orderMeals = new ArrayList<>(List.of(meal, meal1, meal2));
        List<MealDto> mealDtos = new ArrayList<>(List.of());
        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();
        testOrder.setMeals(orderMeals);
        orderDto.setMeals(List.of(meal2));


        when(orderRepository.findById(id)).thenReturn(testOrder);
        when(modelMapper.map(mealDtos, listType)).thenReturn(mealsToDelete);
        when(orderRepository.update(testOrder)).thenReturn(testOrder);
        when(modelMapper.map(testOrder, OrderDto.class)).thenReturn(orderDto);

        OrderDto order = orderService.removeMeals(1, mealDtos);
        assertThat(order.getMeals()).isEqualTo(List.of(meal2));
    }

    //@Test
    @DisplayName("returnAllMealDtos_when_GetAllMeals")
    public void should_returnAllMealDtos_when_tryToGetAllMeals() {

        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();

        Integer orderId = 1;
        Order order = new Order();
        List<Meal> meals = new ArrayList<>();
        List<MealDto> mealDtos = new ArrayList<>();

        when(orderRepository.findOrderByIdWithEntityGraph(orderId)).thenReturn(order);
        when(order.getMeals()).thenReturn(meals);
        when(modelMapper.map(meals, listType)).thenReturn(mealDtos);

        List<MealDto> returnedMealDtos = orderService.getAllMeals(orderId);
        assertThat(returnedMealDtos).isNotNull();
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
