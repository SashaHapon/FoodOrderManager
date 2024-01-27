package org.food.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

