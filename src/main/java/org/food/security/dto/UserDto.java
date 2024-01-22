package org.food.security.dto;

import lombok.*;
import org.food.security.model.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
