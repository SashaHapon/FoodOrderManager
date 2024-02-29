package org.food.dao;

import org.food.api.repository.MealRepository;
import org.food.api.repository.OrderRepository;
import org.food.model.Account;
import org.food.model.Meal;
import org.food.model.Order;
import org.food.testconfig.ContainerConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
@Testcontainers
@Import(ContainerConfiguration.class)
public class OrderRepositoryIntegrationTests {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private JdbcDatabaseContainer<?> databaseContainer;
    private static final String TEST_DATA_FILE_PREFIX = "classpath:data/org/food/dao/OrderServiceIntegrationTests";

    @Test
    @DisplayName("New order must be created")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_create_new_Order/insert_data_for_order.sql")
    public void should_create_new_Order() {
        Meal meal1 = new Meal(1, "Spaghetti Bolognese", new BigDecimal("12.99"), 30);
        Meal meal2 = new Meal(2, "Chicken Caesar Salad", new BigDecimal("9.99"), 20);
        Meal meal3 = new Meal(3, "Grilled Salmon", new BigDecimal("15.99"), 25);
        List<Meal> mealList = mealRepository.findAll(1,3);
        Account account1 = new Account(1, "Test Account 1", new BigDecimal("100.01"), "1234567890");

        Order createdOrder = orderRepository.create(new Order(mealList, account1, new BigDecimal("1222"), 13));
        Order order = orderRepository.findById(createdOrder.getId());
        assertThat(order).isNotNull();
        assertThat(order.getMeals().get(0)).isEqualTo(meal1);
        assertThat(order.getMeals().get(1)).isEqualTo(meal2);
        assertThat(order.getMeals().get(2)).isEqualTo(meal3);
        assertThat(order.getAccount()).isEqualTo(account1);
    }

    @Test
    @DisplayName("Order with id=1 must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_return_order_by_id/insert_order_with_id_1.sql")
    public void should_return_order_by_id() {
        Meal meal1 = new Meal(1, "Spaghetti Bolognese", new BigDecimal("12.99"), 30);
        Account account1 = new Account(1, "Test Account 1", new BigDecimal("100.01"), "1234567890");

        Order order = orderRepository.findById(1);

        assertThat(order).isNotNull();
        assertThat(order.getMeals().get(0)).isEqualTo(meal1);
        assertThat(order.getAccount()).isEqualTo(account1);
    }

    @Test
    @DisplayName("Order with id=2 must be updated")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_update_order_by_id/insert_order_with_id_2.sql")
    public void should_update_order_by_id() {
        Meal meal1 = new Meal(1, "Spaghetti Bolognese", new BigDecimal("12.99"), 30);
        Meal meal2 = new Meal(2, "Chicken Caesar Salad", new BigDecimal("9.99"), 20);
        List<Meal> mealListToUpdate = new ArrayList<>(List.of(meal1, meal2));

        Order order = orderRepository.findById(2);
        order.setMeals(mealListToUpdate);
        orderRepository.update(order);
        Order updatedOrder = orderRepository.findById(2);

        assertThat(updatedOrder.getMeals()).isEqualTo(mealListToUpdate);
    }
}
