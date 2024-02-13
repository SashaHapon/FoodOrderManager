package org.food.model;


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

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal price;
    private int time;

    public Meal(String name, BigDecimal price, int time) {
        this.name = name;
        this.price = price;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(meal.price, price) &&
                Objects.equals(time, meal.time) &&
                Objects.equals(id, meal.id) &&
                Objects.equals(name, meal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, time);
    }
}
