package org.food.security.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

//    @Schema(description = "Имя пользователя", example = "Jon")
    private String username;

//    @Schema(description = "Пароль", example = "my_1secret1_password")
    private String password;
}