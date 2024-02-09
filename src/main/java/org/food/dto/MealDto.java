package org.food.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность блюда")
public class MealDto {
    @Schema(description = "Иднтификатор блюда")
    private Integer id;
    @Schema(description = "Название блюда")
    private String name;
    @Schema(description = "Цена")
    private BigDecimal price;
    @Schema(description = "Время приготовления")
    private int time;

}

