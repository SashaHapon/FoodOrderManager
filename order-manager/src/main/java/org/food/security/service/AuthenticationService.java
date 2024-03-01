package org.food.security.service;

import lombok.RequiredArgsConstructor;
import org.food.exception.classes.TokenRefreshException;
import org.food.security.model.RefreshToken;
import org.food.security.model.Role;
import org.food.security.model.User;
import org.food.security.payload.request.RefreshTokenRequest;
import org.food.security.payload.request.SignInRequest;
import org.food.security.payload.request.SignUpRequest;
import org.food.security.payload.responce.JwtAuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(request).getToken();

        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userName(user.getUsername())
                .build();
        return response;
    }

    /**
     * Аутентификация пользователя
     *
     * @param loginRequest данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = userService.getByUsername(loginRequest.getUsername());

        var accessToken = jwtService.generateToken(user);

        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
        return response;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
        Optional<?> refreshedToken = refreshTokenService.findByToken(request.getRefreshToken());
        if (refreshedToken != null) {
            User user = userService.getByUsername(request.getUserDto().getUsername());

            String newAuthToken = jwtService.generateToken(user);
            RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);
            JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                    .accessToken(newAuthToken)
                    .refreshToken(newRefreshToken.getToken())
                    .build();
            return response;
        } else {
            throw new TokenRefreshException(request.getUserDto().getUsername(), ":RefreshTokenError");
        }
    }
}