package org.food.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность пользователя")
public class AccountDto {
    @Schema(description = "Идентификатор")
    private Integer id;
    @Schema(description = "Имя пользователя")
    private String name;
    @Schema(description = "Сумма на счёте")
    private BigDecimal money;
    @Schema(description = "Телефон пользователя")
    private String phoneNumber;
}
