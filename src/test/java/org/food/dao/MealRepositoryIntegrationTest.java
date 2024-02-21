package org.food.dao;

import org.food.TestUtils;
import org.food.api.repository.MealRepository;
import org.food.model.Meal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
@Testcontainers
public class MealRepositoryIntegrationTest extends TestUtils {
    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private JdbcDatabaseContainer<?> databaseContainer;
    private static final String TEST_DATA_FILE_PREFIX = "classpath:data/org/food/dao/MealRepositoryIntegrationTests";

    @Test
    @DisplayName("All meals must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_returnAllMeals/insert_seven_meals.sql")
    public void should_returnAllMeals() {
        Meal meal0 = new Meal(1, "Spaghetti Bolognese", new BigDecimal("12.99"), 30);
        Meal meal1 = new Meal(2, "Chicken Caesar Salad", new BigDecimal("9.99"), 20);
        Meal meal2 = new Meal(3, "Grilled Salmon", new BigDecimal("15.99"), 25);
        Meal meal3 = new Meal(4, "Test Meal 1", new BigDecimal("15.99"), 30);
        Meal meal4 = new Meal(5, "Test Meal 2", new BigDecimal("15.99"), 30);
        Meal meal5 = new Meal(6, "Test Meal 3", new BigDecimal("15.99"), 30);
        Meal meal6 = new Meal(7, "Test Meal 4", new BigDecimal("15.99"), 30);
        List<Meal> mealList = new ArrayList<>(List.of(meal0, meal1, meal2, meal3, meal4, meal5, meal6));

        List<Meal> meals = mealRepository.findAll(1, 10);

        assertThat(meals).isEqualTo(mealList);
    }

    @Test
    @DisplayName("New meal must be created")
    public void should_addNewMeal() {
        Meal newMeal = new Meal(null, "Draniki", new BigDecimal("15.99"), 2);

        Meal createdMeal = mealRepository.create(newMeal);
        Meal meal = mealRepository.findById(createdMeal.getId());

        assertThat(meal).isEqualTo(createdMeal);
    }

    @Test
    @DisplayName("Meal with id=1 must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_returnMealById/insert_meal_withId_1.sql")
    public void should_returnMealById() {
        Meal meal0 = new Meal(1, "Spaghetti Bolognese", new BigDecimal("12.99"), 30);

        Meal meal = mealRepository.findById(1);

        assertThat(meal).isEqualTo(meal0);
    }

    @Test
    @DisplayName("Meal with id=3 must be deleted")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_deleteMeal/insert_meal_with_id_3.sql")
    public void should_deleteMeal() {
        Meal mealBefore = mealRepository.findById(3);
        mealRepository.delete(mealBefore);
        Meal meal = mealRepository.findById(3);

        assertThat(mealBefore).isNotNull();
        assertThat(meal).isNull();
    }

    @Test
    @DisplayName("Meal with id=4 must be updated")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_updateMeal/insert_meal_with_id_4.sql")
    public void should_updateMeal() {
        Meal mealToUpdate = new Meal(4, "CocaCola", new BigDecimal("15.99"), 2);

        mealRepository.update(mealToUpdate);
        Meal meal = mealRepository.findById(4);

        assertThat(meal).isEqualTo(mealToUpdate);
    }
}
