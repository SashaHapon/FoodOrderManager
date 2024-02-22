package org.receipt.dto;

import lombok.Data;
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
