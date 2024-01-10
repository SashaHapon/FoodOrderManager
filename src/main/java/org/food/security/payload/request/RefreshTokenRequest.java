package org.food.security.payload.request;

import lombok.Data;
import org.food.security.dto.UserInfo;

@Data
public class RefreshTokenRequest {
    private UserInfo userInfo;
    private String refreshToken;
}
