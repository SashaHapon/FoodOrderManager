package org.food.security.controller;

import lombok.RequiredArgsConstructor;
import org.food.security.dto.UserDto;
import org.food.security.model.User;
import org.food.security.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{name}")
    public UserDto getUser(@PathVariable("name") String name ){
        UserDto user = userService.getUserDtoByUsername(name);
        return user;
    }
}
