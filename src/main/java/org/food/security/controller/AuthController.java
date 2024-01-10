package org.food.security.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.food.security.payload.request.RefreshTokenRequest;
import org.food.security.payload.request.SignInRequest;
import org.food.security.payload.request.SignUpRequest;
import org.food.security.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//<editor-fold desc="Description">
@RequiredArgsConstructor
//</editor-fold>
//@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthenticationService authenticationService;

    //    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    //    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity refreshToken(@RequestBody @Valid RefreshTokenRequest refreshToken) {
        System.out.println("/auth");
        return authenticationService.refreshToken(refreshToken);
    }
}
