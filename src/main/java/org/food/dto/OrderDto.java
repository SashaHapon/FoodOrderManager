package org.food.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "Сущность заказа")
public class OrderDto {
    @Schema(description = "Идентификатор заказа")
    private Integer id;
    @Schema(description = "Список блюд")
    private List<MealDto> meals;
    //todo needed??
//    private String accountId;
//
    @Schema(description = "Сущность пользователя")
    private AccountDto account;
    @Schema(description = "Сумма заказа")
    private BigDecimal orderSum;
    @Schema(description = "Время приготовления")
    private int cookingTimeSum;

}
