package org.food.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Integer id;

    private List<MealDto> meals;

    private String accountId;

    private AccountDto accountDto;

}
