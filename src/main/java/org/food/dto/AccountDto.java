package org.food.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
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
