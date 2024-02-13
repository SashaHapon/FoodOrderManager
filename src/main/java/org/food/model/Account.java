package org.food.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Objects;

@Table(name = "account")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(name, account.name) &&
                Objects.equals(money, account.money) &&
                Objects.equals(phoneNumber, account.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, money, phoneNumber);
    }
}
