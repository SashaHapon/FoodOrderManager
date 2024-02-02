package org.food.model;


import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;

@Table(name = "account")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private BigDecimal money;

    @Column(name = "phone_number")
    private String phoneNumber;
}
