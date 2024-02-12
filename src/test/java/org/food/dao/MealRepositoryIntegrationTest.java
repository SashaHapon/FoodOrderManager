package org.food.dao;

import org.food.api.repository.MealRepository;
import org.food.model.Meal;
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
public class MealRepositoryIntegrationTest {
    @Autowired
    private MealRepository mealRepository;
    private static final String TEST_DATA_FILE_PREFIX = "classpath:data/org/food/dao/MealRepositoryIntegrationTests";
    int id = 1;
    int limit = 10;

    private Meal meal0;
    private Meal meal1;
    private Meal meal2;
    private Meal meal3;
    private Meal meal4;
    private Meal meal5;
    private Meal meal6;
    private Meal newMeal;
    private Meal mealToUpdate;
    private List<Meal> mealList;

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
    public void init() {
        meal0 = new Meal(1, "Spaghetti Bolognese", new BigDecimal("12.99"), 30);
        meal1 = new Meal(2, "Chicken Caesar Salad", new BigDecimal("9.99"), 20);
        meal2 = new Meal(3, "Grilled Salmon", new BigDecimal("15.99"), 25);
        meal3 = new Meal(4, "Test Meal 1", new BigDecimal("15.99"), 30);
        meal4 = new Meal(5, "Test Meal 2", new BigDecimal("15.99"), 30);
        meal5 = new Meal(6, "Test Meal 3", new BigDecimal("15.99"), 30);
        meal6 = new Meal(7, "Test Meal 4", new BigDecimal("15.99"), 30);
        newMeal = new Meal("Draniki", new BigDecimal("15.99"), 2);
        mealToUpdate = new Meal(4, "CocaCola", new BigDecimal("15.99"), 2);
        mealList = new ArrayList<>(List.of(meal0, meal1, meal2, meal3, meal4, meal5, meal6));
    }

    @Test
    @DisplayName("Account with id=1 must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_returnAllMeals/insert_seven_meals.sql")
    public void should_returnAllMeals() {
        List<Meal> meals = mealRepository.findAll(id, limit);
        assertThat(meals).isEqualTo(mealList);
    }

    @Test
    @DisplayName("New account must be created")
    public void should_addNewMeal() {
        Meal createdMeal = mealRepository.create(newMeal);
        Meal meal = mealRepository.findById(2);
        assertThat(meal).isEqualTo(createdMeal);
    }

    ;

    @Test
    @DisplayName("Account with id=1 must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_returnMealById/insert_meal_withId_1.sql")
    public void should_returnMealById() {
        Meal meal = mealRepository.findById(1);
        assertThat(meal).isEqualTo(meal0);
    }

    ;

    @Test
    @DisplayName("")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_deleteMeal/insert_meal_with_id_3.sql")
    public void should_deleteMeal() {
        Meal mealBefore = mealRepository.findById(3);
        mealRepository.delete(meal2);
        Meal meal = mealRepository.findById(3);
        assertThat(mealBefore).isNotNull();
        assertThat(meal).isNull();
    }

    ;

    @Test
    @DisplayName("")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_updateMeal/insert_meal_with_id_4.sql")
    public void should_updateMeal() {
        mealRepository.update(mealToUpdate);
        Meal meal = mealRepository.findById(4);
        assertThat(meal).isEqualTo(mealToUpdate);
    }

    ;
}
