package org.food.security.payload.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    //better to use userId, but i'll spend a lot of time
    private String userName;
    private String accessToken;
    private String refreshToken;
}