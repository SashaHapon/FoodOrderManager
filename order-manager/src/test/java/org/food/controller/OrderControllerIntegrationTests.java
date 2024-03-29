package org.food.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.BaseTests;
import org.food.api.service.OrderService;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//todo enable filters
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
@Transactional
@Rollback
@EmbeddedKafka
public class OrderControllerIntegrationTests extends BaseTests {
    private static final String TEST_DATA_FILE_PREFIX = "classpath:data/org/food/controller/OrderControllerIntegrationTest";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderService orderService;

    @Test
    @WithMockUser
    @Sql("classpath:data/org/food/controller/OrderControllerIntegrationTest/should_createOrder/testdata.sql")
    public void should_createOrder() throws Exception {
        mockMvc.perform(post("/orders/").param("id", "1")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonAsString(TEST_DATA_FILE_PREFIX + "/should_createOrder/createOrder_inputMealsDtoList.json")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString(TEST_DATA_FILE_PREFIX + "/should_createOrder/createOrder_expectedJson.json")));
    }

    @Test
    @WithMockUser
    @DisplayName("Return order from database with id=1")
    @Sql("classpath:data/org/food/controller/OrderControllerIntegrationTest/should_return_Order_with_id1/testdata.sql")
    void should_return_Order_with_id1() throws Exception {

        mockMvc.perform(get("/orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString(TEST_DATA_FILE_PREFIX + "/should_return_Order_with_id1/getOrder_expectedJson.json")));
    }

    @Test
    @WithMockUser
    @DisplayName("Add meals to order with id=3")
    @Sql("classpath:data/org/food/controller/OrderControllerIntegrationTest/should_addMeals_toOrder/testdata.sql")
    public void should_addMeals_toOrder() throws Exception {

        List<MealDto> mealListBeforeAdding = orderService.getOrder(3).getMeals();
        mockMvc.perform(put("/orders/3").param("id", "3")
                        .content(getJsonAsString(TEST_DATA_FILE_PREFIX + "/should_addMeals_toOrder/addMeals_inputMeal.json"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        List<MealDto> mealListAfterAdding = orderService.getOrder(1).getMeals();
        assertNotNull(mealListBeforeAdding);
        assertNotEquals(mealListAfterAdding, mealListBeforeAdding);
    }

    @Test
    @WithMockUser
    @DisplayName("Remove meals from order with id=2")
    @Sql("classpath:data/org/food/controller/OrderControllerIntegrationTest/should_removeMeals_fromOrder/testdata.sql")
    public void should_removeMeals_fromOrder() throws Exception {

        OrderDto orderBeforeDeleting = orderService.getOrder(2);
        mockMvc.perform(delete("/orders/2").param("orderId", "2")
                        .content(getJsonAsString(TEST_DATA_FILE_PREFIX + "/should_removeMeals_fromOrder/removeMeals_inputMeal.json"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        OrderDto orderAfterDeleting = orderService.getOrder(2);
        assertNotNull(orderAfterDeleting);
        assertNotEquals(orderAfterDeleting, orderBeforeDeleting);
    }

    @Test
    @WithMockUser
    @DisplayName("Get all meals from order")
    @Sql("classpath:data/org/food/controller/OrderControllerIntegrationTest/should_getAllMeals_fromOrder/testdata.sql")
    public void should_getAllMeals_fromOrder() throws Exception {

        List<MealDto> listM = orderService.getOrder(1).getMeals();
        MvcResult mvcResult = mockMvc.perform(get("/orders/1/meals").param("orderId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<MealDto> mealDtos = objectMapper.readValue(contentAsString, new TypeReference<List<MealDto>>() {
        });

        assertEquals(mealDtos, listM);
    }
}
