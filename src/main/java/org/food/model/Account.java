package org.food.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Table(name = "account")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private BigDecimal money;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Account(String name, BigDecimal money, String phoneNumber) {
        this.name = name;
        this.money = money;
        this.phoneNumber = phoneNumber;
    }
}
