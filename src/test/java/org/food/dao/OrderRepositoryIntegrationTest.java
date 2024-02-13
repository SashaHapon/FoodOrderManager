package org.food.dao;

import org.food.api.repository.OrderRepository;
import org.food.model.Account;
import org.food.model.Meal;
import org.food.model.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
@Rollback
public class OrderRepositoryIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;
    private static final String TEST_DATA_FILE_PREFIX = "classpath:data/org/food/dao/OrderServiceIntegrationTests";

    public Account account1;
    public Account account2;
    public Account account3;

    public Meal meal1;
    public Meal meal2;
    public Meal meal3;
    List<Meal> mealList;
    List<Meal> mealList1;
    public List<Meal> mealListToUpdate;

    public Order newOrder;
    public Order order1;
    public Order updatedOrder;

    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @BeforeEach
    public void init(){
        account1 = new Account(1,"Test Account 1", new BigDecimal("100.01"), "1234567890");
        account2 = new Account(2,"Test Account 2", new BigDecimal("100.01"), "1234567890");
        account3 = new Account(3,"Test Account 3", new BigDecimal("100.01"), "1234567890");

        meal1 = new Meal(1, "Spaghetti Bolognese", new BigDecimal("12.99"), 30);
        meal2 = new Meal(2, "Chicken Caesar Salad", new BigDecimal("9.99"), 20);
        meal3 = new Meal(3, "Grilled Salmon", new BigDecimal("15.99"), 25);

        mealList = new ArrayList<>(List.of(meal1, meal2, meal3));
        mealList1 = new ArrayList<>(List.of(meal1));
        mealListToUpdate = new ArrayList<>(List.of(meal1, meal2));

        newOrder = new Order(mealList, account1, new BigDecimal("1222"), 13);
        order1 = new Order(1, mealList1, account1, new BigDecimal("50.99"), 30);
        updatedOrder = new Order(2, mealListToUpdate, account1, new BigDecimal("50.99"), 30);
    }
    @Test
    @DisplayName("New meal must be created")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_create_new_Order/insert_data_for_order.sql")
    public void should_create_new_Order() {
        orderRepository.create(newOrder);
        Order order = orderRepository.findById(3);
        assertThat(order).isEqualTo(newOrder);
    }

    @Test
    @DisplayName("Meal with id=1 must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_return_order_by_id/insert_order_with_id_1.sql")
    public void should_return_order_by_id() {
        Order order = orderRepository.findById(1);
        assertThat(order).isEqualTo(order1);
    }

    @Test
    @DisplayName("Meal with id=1 must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_update_order_by_id/insert_order_with_id_2.sql")
    public void should_update_order_by_id() {
        Order order = orderRepository.findById(2);
        order.setMeals(mealListToUpdate);
        orderRepository.update(order);
        assertThat(order).isEqualTo(updatedOrder);
    }
}
