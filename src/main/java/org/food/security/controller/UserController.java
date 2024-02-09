package org.food.security.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.food.security.dto.UserDto;
import org.food.security.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "UserController", description = " Позволяет получить полозователя из бд")
public class UserController {
    private final UserService userService;

    @GetMapping("/{name}")
    public UserDto getUser(@PathVariable("name")
                           @Parameter(description = "Запрос на получение пользователя") String name) {
        UserDto user = userService.getUserDtoByUsername(name);
        return user;
    }
}
