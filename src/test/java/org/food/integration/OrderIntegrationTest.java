package org.food.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.api.repository.OrderRepository;
import org.food.api.service.OrderService;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
            Path path = Paths.get("src/test/resources/db/data/order/integration/test/" + name);
            return Files.readString(path);
        }catch (IOException e){
            throw new RuntimeException(e.getCause());
        }
    }

    @Test
    @WithMockUser
    @Sql("classpath:db/data/testdata.sql")
    public void should_createOrder() throws Exception, IOException {

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
    @Sql("classpath:db/data/testdata.sql")
    void should_return_Order_with_id1() throws Exception {

        mockMvc.perform(get("/orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString("getOrder_expectedJson.json")));
    }

    @Test
    @WithMockUser
    @DisplayName("Add meals to order with id=3")
    @Sql("classpath:db/data/testdata.sql")
    public void should_addMeals_toOrder() throws Exception {

        List<MealDto> mealListBeforeAdding = orderService.getOrder(3).getMeals();
        mockMvc.perform(put("/orders/3").param("id", "3")
                        .content(getJsonAsString("addMeals_inputMeal.json"))
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
    @Sql("classpath:db/data/testdata.sql")
    public void should_removeMeals_fromOrder() throws Exception {

        OrderDto orderBeforeDeleting = orderService.getOrder(2);
        mockMvc.perform(delete("/orders/2").param("orderId", "2")
                        .content(getJsonAsString("removeMeals_inputMeal.json"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        OrderDto orderAfterDeleting = orderService.getOrder(2);
        assertNotNull(orderAfterDeleting);
        assertNotEquals(orderAfterDeleting, orderBeforeDeleting);
    }

    @Test
    @WithMockUser
    @DisplayName("Get all meals from order with id=1")
    @Sql("classpath:db/data/testdata.sql")
    public void should_getAllMeals_fromOrder() throws Exception {

        List<MealDto> listM = orderService.getOrder(1).getMeals();
        MvcResult mvcResult = mockMvc.perform(get("/orders/meals/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<MealDto> mealDtos = objectMapper.readValue(contentAsString, new TypeReference<List<MealDto>>() {
        });

        assertEquals(mealDtos, listM);
    }
}
