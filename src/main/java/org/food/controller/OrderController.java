package org.food.controller;

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
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/")
    public OrderDto createOrder(@RequestParam("id") Integer accountId,
                            @RequestBody List<MealDto> mealDtoList) {

        return orderService.createOrder(accountId, mealDtoList);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable("id") Integer id) {

        return orderService.getOrder(id);
    }

    @PutMapping("/{id}")
    public void addMeals(@PathVariable("id") Integer orderId, @RequestBody  List<MealDto> mealDtos) {

        orderService.addMeals(orderId,  mealDtos);
    }

    @DeleteMapping("/{id}")
    public void removeMeals(@PathVariable("id") Integer orderId,
                            @RequestBody List<MealDto> mealDtosToRemove) {

        orderService.removeMeals(orderId, mealDtosToRemove);
    }

    @GetMapping("/{orderId}/meals")
    public List<MealDto> getAllMeals(@PathVariable("orderId") Integer id) {

        return orderService.getAllMeals(id);
    }
}
