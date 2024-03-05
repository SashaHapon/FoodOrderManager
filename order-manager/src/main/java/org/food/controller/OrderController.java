package org.food.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.food.api.service.OrderService;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.food.dto.ReceiptDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(name = "OrderController", description = " Позволяет контролировать заказы в бд")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Создание заказа",
            description = "Позволяет создать заказ в базе данных"
    )
    @PostMapping("/")
    public OrderDto createOrder(@RequestParam("id")
                                @Parameter(description = "Индентификатор аккаунта") Integer accountId,
                                @RequestBody
                                @Parameter(description = "Список блюд")
                                List<MealDto> mealDtoList) throws JsonProcessingException {

        return orderService.createOrder(accountId, mealDtoList);
    }

    @Operation(
            summary = "Получение заказа",
            description = "Позволяет получить заказ из базы данных"
    )
    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable("id")
                             @Parameter(description = "Идентификатор заказа") Integer id) {

        return orderService.getOrder(id);
    }

    //todo
    @GetMapping("/{id}/receipt")
    public ReceiptDto printReceipt(@PathVariable("id")
                                   @Parameter(description = "Идентификатор заказа") Integer id) {

        return orderService.printReceipt(id);
    }

    @Operation(
            summary = "Добавление блюда в заказ",
            description = "Позволяет создать заказ в базе данных"
    )
    @PutMapping("/{id}")
    public void addMeals(@PathVariable("id")
                         @Parameter(description = "Идентификатор заказа") Integer orderId,
                         @RequestBody
                         @Parameter(description = "Список блюд") List<MealDto> mealDtos) {

        orderService.addMeals(orderId, mealDtos);
    }

    @Operation(
            summary = "Удаление блюд из заказа",
            description = "Позволяет удалить блюда из заказа в базе данных"
    )
    @DeleteMapping("/{id}")
    public void removeMeals(@PathVariable("id")
                            @Parameter(description = "Идентификатор заказа") Integer orderId,
                            @RequestBody
                            @Parameter(description = "Список блюд") List<MealDto> mealDtosToRemove) {

        orderService.removeMeals(orderId, mealDtosToRemove);
    }

    @Operation(
            summary = "Получение всех блюд заказа",
            description = "Позволяет получить все блюда из заказа в базе данных"
    )
    @GetMapping("/{orderId}/meals")
    public List<MealDto> getAllMeals(@PathVariable("orderId")
                                     @Parameter(description = "Идентификатор заказа") Integer id) {

        return orderService.getAllMeals(id);
    }
}
