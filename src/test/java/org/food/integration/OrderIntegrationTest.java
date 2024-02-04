package org.food.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.api.repository.OrderRepository;
import org.food.api.service.AccountService;
import org.food.api.service.MealService;
import org.food.api.service.OrderService;
import org.food.dao.OrderRepositoryImpl;
import org.food.dto.AccountDto;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.food.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//todo enable filters
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
@Rollback
public class OrderIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    private String getJsonAsString(String name){
        try {
            Path path = Paths.get("src/test/resources/db/data/sql/order/integration/test/" + name);
            return Files.readString(path);
        }catch (IOException e){
            throw new RuntimeException(e.getCause());
        }
    }

    @Test
    @WithMockUser
    @Sql("classpath:db/data/sql/order/integration/test/orders-sql-testdata.sql")
    public void createOrder() throws Exception, IOException {

        mockMvc.perform(post("/orders/").param("id", "1")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonAsString("createOrder_inputMealsDtoList.json")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString("createOrder_expectedJson.json")));
    }

    @Test
    @WithMockUser
    @DisplayName("Return order from database with id=1")
    @Sql("classpath:db/data/sql/order/integration/test/orders-sql-testdata.sql")
    void should_return_Order_with_id1() throws Exception {

        mockMvc.perform(get("/orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString("getOrder_expectedJson.json")));
    }

    //todo to 3 id order
    @Test
    @WithMockUser
    @DisplayName("Add meals to order with id=1")
    @Sql("classpath:db/data/sql/order/integration/test/orders-sql-testdata.sql")
    public void addMeals() throws Exception {

        List<MealDto> mealListBeforeAdding = orderService.getOrder(1).getMeals();
        mockMvc.perform(put("/orders/1").param("id", "1")
                        .content(getJsonAsString("addMeals_inputMeal.json"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        List<MealDto> mealListAfterAdding = orderService.getOrder(1).getMeals();
        assertNotEquals(mealListAfterAdding, mealListBeforeAdding);
    }

    //todo to 2id
    @Test
    @WithMockUser
    @DisplayName("Add meals to order with id=1")
    @Sql("classpath:db/data/sql/order/integration/test/orders-sql-testdata.sql")
    public void removeMeals() throws Exception {

        OrderDto orderBeforeDeleting = orderService.getOrder(1);
        mockMvc.perform(delete("/orders/1").param("orderId", "1")
                        .content(getJsonAsString("removeMeals_inputMeal.json"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        OrderDto orderAfterDeleting = orderService.getOrder(1);
        assertNotNull(orderAfterDeleting);
        assertNotEquals(orderAfterDeleting, orderBeforeDeleting);
    }

    @Test
    @WithMockUser
    @DisplayName("Add meals to order with id=1")
    @Sql("classpath:db/data/sql/order/integration/test/orders-sql-testdata.sql")
    public void getAllMeals() throws Exception {

        List<MealDto> listM = orderService.getOrder(1).getMeals();
        MvcResult mvcResult = mockMvc.perform(get("/orders/meals/1").param("id", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<MealDto> mealDtos = objectMapper.readValue(contentAsString, new TypeReference<List<MealDto>>() {
        });

        assertEquals(mealDtos, listM);
    }
}
