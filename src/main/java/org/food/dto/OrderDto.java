package org.food.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

    private Integer id;

    private List<MealDto> meals;
//todo needed??
//    private String accountId;
//
    private AccountDto account;

    private BigDecimal orderSum;

    private int cookingTimeSum;

}
