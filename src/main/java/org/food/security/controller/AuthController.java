package org.food.security.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.food.security.dto.UserDto;
import org.food.security.payload.request.RefreshTokenRequest;
import org.food.security.payload.request.SignInRequest;
import org.food.security.payload.request.SignUpRequest;
import org.food.security.payload.responce.JwtAuthenticationResponse;
import org.food.security.service.AuthenticationService;
import org.food.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private  final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity <UserDto> signUp(@RequestBody @Valid SignUpRequest request) {

        JwtAuthenticationResponse response = authenticationService.signUp(request);
        UserDto userDto = userService.getUserDtoByUsername(response.getUserName());
        return ResponseEntity.ok().header("AccessToken", response.getAccessToken())
                .header("RefreshToken", response.getRefreshToken())
                .body(userDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity <UserDto> signIn(@RequestBody @Valid SignInRequest request) {
        JwtAuthenticationResponse response = authenticationService.signIn(request);
        return ResponseEntity.ok().header("accessToken", response.getAccessToken()).build();
    }

    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity <UserDto> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshToken) {
        JwtAuthenticationResponse response = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok().header("RefreshToken", response.getRefreshToken()).build();
    }
}
