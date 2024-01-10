package org.food.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.food.security.payload.request.SignInRequest;
import org.food.security.payload.request.SignUpRequest;
import org.food.security.service.AuthenticationService;
import org.food.security.service.UserDetailsImpl;
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

    @GetMapping("/refreshToken")
    public ResponseEntity refreshToken(String refreshToken, UserDetailsImpl userDetails) {
        return authenticationService.refreshToken(refreshToken, userDetails);
    }
}
