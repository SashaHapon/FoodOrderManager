package org.food.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.api.service.MealService;
import org.food.dto.MealDto;
import org.food.exception.classes.NotFoundException;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//todo enable filters
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
@Rollback
public class MealIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MealService mealService;

    private String getJsonAsString(String name){
        try {
            Path path = Paths.get("src/test/resources/db/data/meal/integration/test/" + name);
            return Files.readString(path);
        }catch (IOException e){
            throw new RuntimeException(e.getCause());
        }
    }

    @Test
    @DisplayName("Return meal from database with id=1")
    @Sql("classpath:db/data/testdata.sql")
    void should_return_Meal_with_id1() throws Exception {

        ResultActions result = mockMvc.perform(get("/meals/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString("getMeal_expectedMealDto.json")));
    }

    @Test
    @DisplayName("Throw NotFoundException when try to get meal with id=0)")
    void should_throw_notFoundException_when_getMeal() throws Exception {

        ResultActions result = mockMvc.perform(get("/meals/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Add new meal to database")
    void should_addAccount_toDb() throws Exception {

        MealDto mealDto = new MealDto(null, "Water", BigDecimal.valueOf(1), 15);

        String requestBody = objectMapper.writeValueAsString(mealDto);

        ResultActions resultActsions = mockMvc.perform(post("/meals/")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActsions.andReturn();


        String content = result.getResponse().getContentAsString();
        System.out.println(content);
        MealDto createdMealDto = objectMapper.readValue(content, MealDto.class);

        assertNotNull(createdMealDto);
        assertEquals("Water", createdMealDto.getName());
    }

    @Test
    @WithMockUser
    @DisplayName("Throw BadRequestException because input json is incorrect")
    void should_throwException_when_tryToAddMeal() throws Exception {


        String requestBody = objectMapper.writeValueAsString("incorrect message");

        ResultActions result = mockMvc.perform(post("/meals/")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Return all meals from database")
    @Sql("classpath:db/data/testdata.sql")
    public void should_return_allMeals() throws Exception {

        ResultActions result = mockMvc.perform(get("/meals/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString("getAllMeals_expectedMealDtoList.json")));

    }


    @Test
    @DisplayName("Delete meal with id=3")
    @Sql("classpath:db/data/testdata.sql")
    public void should_delete_meal_withId_3() throws Exception {
        ResultActions result = mockMvc.perform(delete("/meals/3"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> mealService.getMeal(3));
    }


    @Test
    @DisplayName("Throw NotFoundException when try to delete meal with incorrect id")
    public void deleteNonExistingMealById() throws Exception {
        mockMvc.perform(delete("/meals/404"))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> mealService.deleteMealById(404));
    }

    @Test
    @DisplayName("Update meal with id=4")
    @Sql("classpath:db/data/testdata.sql")
    void should_update_meal_with_id_4() throws Exception {

        mockMvc.perform(put("/meals/4")
                        .content(getJsonAsString("updateMeal_inputMealDto.json"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MealDto mealDto = mealService.getMeal(4);
        MealDto inputDto = objectMapper.readValue(getJsonAsString("updateMeal_inputMealDto.json"), MealDto.class);
        assertEquals(mealDto, inputDto);
    }
}







