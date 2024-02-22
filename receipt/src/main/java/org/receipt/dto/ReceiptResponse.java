package org.receipt.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReceiptResponse {
    private Integer id;
    private List<MealDto> meals;
    private AccountDto account;
    private BigDecimal orderSum;
    private int cookingTimeSum;
}
