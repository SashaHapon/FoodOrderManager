package org.food.security.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity(name = "accesstoken")
@Data
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
