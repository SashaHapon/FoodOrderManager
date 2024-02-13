package org.food.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Meal.class)
    private List<Meal> meals = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class)
    private Account account;

    private BigDecimal orderSum;

    private int cookingTimeSum;

    public Order(List<Meal> meals, Account account, BigDecimal orderSum, int cookingTimeSum) {
        this.meals = meals;
        this.account = account;
        this.orderSum = orderSum;
        this.cookingTimeSum = cookingTimeSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(meals, order.meals) &&
                Objects.equals(account, order.account) &&
                Objects.equals(orderSum, order.orderSum) &&
                Objects.equals(cookingTimeSum, order.cookingTimeSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meals, account, orderSum, cookingTimeSum);
    }
}
