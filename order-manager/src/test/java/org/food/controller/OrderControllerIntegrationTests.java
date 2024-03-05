package org.food.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.BaseTests;
import org.food.api.service.OrderService;
import org.food.dto.MealDto;
import org.food.dto.OrderDto;
import org.food.testconfig.ContainerConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.kafka.test.utils.KafkaTestUtils.consumerProps;
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
@EmbeddedKafka(partitions = 1,
        topics = {
                "kitchen-response",
                "order-message" })
@Import(ContainerConfiguration.class)
public class OrderControllerIntegrationTests extends BaseTests {
    private static final String TEST_DATA_FILE_PREFIX = "classpath:data/org/food/controller/OrderControllerIntegrationTest";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    @WithMockUser
    @Sql("classpath:data/org/food/controller/OrderControllerIntegrationTest/should_createOrder/testdata.sql")
    public void should_createOrder() throws Exception {

//        KitchenResponse response = new KitchenResponse();
//        response.setOrderId(1);
//        response.setCookingTime(20);
//        String string = objectMapper.writeValueAsString(response);
//
//        Map<String, Object> configs = KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker);
//        Consumer<Integer, String> consumer = new DefaultKafkaConsumerFactory<>(configs, new IntegerDeserializer(), new StringDeserializer()).createConsumer();
//        consumer.subscribe(singleton("order-message"));
//
//        Map<String, Object> configsProd = KafkaTestUtils.producerProps(embeddedKafkaBroker);
//        Producer<Integer, String> producer = new DefaultKafkaProducerFactory<>(configsProd, new IntegerSerializer(), new StringSerializer()).createProducer();

        MvcResult result = mockMvc.perform(post("/orders/").param("id", "1")
                    .contentType(APPLICATION_JSON)
                    .content(getJsonAsString(TEST_DATA_FILE_PREFIX + "/should_createOrder/createOrder_inputMealsDtoList.json")))
            .andDo(print())
                .andReturn();
//            .andExpect(status().isOk())
//            .andExpect(content().json(getJsonAsString(TEST_DATA_FILE_PREFIX + "/should_createOrder/createOrder_expectedJson.json")))
//            .andReturn();
//        ConsumerRecord<Integer, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "order-message", Duration.of(5, ChronoUnit.SECONDS));
//
//        producer.send(new ProducerRecord<>("kitchen-response", 1, string));
//        producer.flush();
//
//        assertThat(singleRecord).isNotNull();
        System.out.println(orderService.getOrder(1));
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
