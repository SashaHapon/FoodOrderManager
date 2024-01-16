package org.food.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Integer id;

    private String name;

    private BigDecimal money;

    private String phoneNumber;
}
