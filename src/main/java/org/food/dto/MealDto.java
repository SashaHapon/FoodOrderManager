package org.food.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {

    private Integer id;

    private String name;

    private BigDecimal price;

    private int time;

}

