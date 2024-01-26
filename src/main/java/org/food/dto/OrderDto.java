package org.food.dto;

import org.food.model.Meal;
import lombok.*;

import java.util.List;

@Data
public class OrderDto {

    private Integer id;

    private List<MealDto> meals;

    private String accountId;

    private AccountDto accountDto;

    private MealDto mealDto;

}
