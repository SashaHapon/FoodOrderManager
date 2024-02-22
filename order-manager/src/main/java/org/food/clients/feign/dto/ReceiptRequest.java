package org.food.clients.feign.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.food.dto.AccountDto;
import org.food.dto.MealDto;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReceiptRequest{
    private Integer id;
    private List<MealDto> meals;
    private AccountDto account;
    private BigDecimal orderSum;
    private int cookingTimeSum;
}
