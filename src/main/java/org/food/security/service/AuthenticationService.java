package org.food.security.service;

import lombok.RequiredArgsConstructor;
import org.food.exception.classes.TokenRefreshException;
import org.food.security.dto.UserInfo;
import org.food.security.model.RefreshToken;
import org.food.security.model.Role;
import org.food.security.model.User;

import org.food.security.payload.request.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.food.security.payload.request.SignInRequest;
import org.food.security.payload.request.SignUpRequest;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public ResponseEntity signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(request).getToken();

        return ResponseEntity.ok().header("AccessToken", accessToken)
                .header("RefreshToken", refreshToken)
                .body(new UserInfo(user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()));
    }

    /**
     * Аутентификация пользователя
     *
     * @param loginRequest данные пользователя
     * @return токен
     */
    public ResponseEntity signIn(SignInRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = userService
                .userDetailsService()
                .loadUserByUsername(loginRequest.getUsername());

        var jwt = jwtService.generateToken(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity refreshToken(RefreshTokenRequest request){
        Optional<?> refreshedToken = refreshTokenService.findByToken(request.getRefreshToken());
        if (refreshedToken != null) {
            User user = userService.getByUsername(request.getUserInfo().getUsername());

            String newAuthToken = jwtService.generateToken(user);
            RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

            return ResponseEntity.ok().header("accessToken", newAuthToken)
                    .header("refreshToken", newRefreshToken.getToken())
                    .build();
        } else {
            throw new TokenRefreshException(request.getUserInfo().getUsername(), ":RefreshTokenError");
        }
    }
}