package org.food.security.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.food.security.model.Role;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponce {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
