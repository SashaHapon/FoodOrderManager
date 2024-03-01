package org.food.security.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.food.security.dto.UserDto;

@Data
@Schema(description = "Запрос на обновление токена")
public class RefreshTokenRequest {
    private UserDto userDto;
    private String refreshToken;
}
