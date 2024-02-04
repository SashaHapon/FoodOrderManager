package org.food.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Meal.class)
    private List<Meal> meals = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Account.class)
    private Account account;

    private BigDecimal orderSum;

    private int cookingTimeSum;
}
