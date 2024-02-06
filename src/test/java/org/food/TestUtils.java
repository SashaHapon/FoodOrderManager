package org.food;

import org.food.controller.AccountController;
import org.food.controller.MealController;
import org.food.controller.OrderController;
import org.food.model.Meal;
import org.food.model.Order;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class TestUtils {

    public String getJsonAsString(String fileName, AccountController accountController) throws IOException {
        String string = Files.readString(ResourceUtils
                .getFile("classpath:data/org/food/controller/AccountControllerIntegrationTest" + fileName).toPath());
        return string;
    }

    public String getJsonAsString(String fileName, MealController mealController) throws IOException {
        String string = Files.readString(ResourceUtils
                .getFile("classpath:data/org/food/controller/MealControllerIntegrationTest" + fileName).toPath());
        return string;
    }
    public String getJsonAsString(String fileName, OrderController OrderController) throws IOException {
        String string = Files.readString(ResourceUtils
                .getFile("classpath:data/org/food/controller/OrderControllerIntegrationTest" + fileName).toPath());
        return string;
    }
}

