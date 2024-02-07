package org.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.food.api.service.OrderService;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name="OrderController", description=" Позволяет контролировать заказы в бд")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Создание заказа",
            description = "Позволяет создать заказ в базе данных"
    )
    @PostMapping("/")
    public OrderDto createOrder(@RequestParam("id") Integer accountId,
                            @RequestBody List<MealDto> mealDtoList) {

        return orderService.createOrder(accountId, mealDtoList);
    }

    @Operation(
            summary = "Получение заказа",
            description = "Позволяет получить заказ из базы данных"
    )
    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable("id") Integer id) {

        return orderService.getOrder(id);
    }

    @Operation(
            summary = "Добавление блюда в заказ",
            description = "Позволяет создать заказ в базе данных"
    )
    @PutMapping("/{id}")
    public void addMeals(@PathVariable("id") Integer orderId, @RequestBody  List<MealDto> mealDtos) {

        orderService.addMeals(orderId,  mealDtos);
    }

    @Operation(
            summary = "Удаление блюд из заказа",
            description = "Позволяет удалить блюда из заказа в базе данных"
    )
    @DeleteMapping("/{id}")
    public void removeMeals(@PathVariable("id") Integer orderId,
                            @RequestBody List<MealDto> mealDtosToRemove) {

        orderService.removeMeals(orderId, mealDtosToRemove);
    }

    @Operation(
            summary = "Получение всех блюд заказа",
            description = "Позволяет получить все блюда из заказа в базе данных"
    )
    @GetMapping("/{orderId}/meals")
    public List<MealDto> getAllMeals(@PathVariable("orderId") Integer id) {

        return orderService.getAllMeals(id);
    }
}
