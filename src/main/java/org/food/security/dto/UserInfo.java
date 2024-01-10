package org.food.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.food.security.model.Role;

@Getter
@Setter
@AllArgsConstructor
public class UserInfo {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
