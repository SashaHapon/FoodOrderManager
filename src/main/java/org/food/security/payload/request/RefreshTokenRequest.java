package org.food.security.payload.request;

import lombok.Data;
import org.food.security.dto.UserDto;

@Data
public class RefreshTokenRequest {
    private UserDto userDto;
    private String refreshToken;
}
