package org.food.security.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size
    private  String password;
}
