package org.food.security.controller;

import lombok.RequiredArgsConstructor;
import org.food.security.dto.UserInfo;
import org.food.security.model.User;
import org.food.security.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{name}")
    public UserInfo getUser(@PathVariable("name") String name ){
        User user = userService.getByUsername(name);
        return new UserInfo(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole());
    }
}
