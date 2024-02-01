package org.food.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.api.repository.AccountRepository;
import org.food.api.repository.MealRepository;
import org.food.api.repository.OrderRepository;
import org.food.controller.AccountController;
import org.food.security.repository.RefreshTokenRepository;
import org.food.security.repository.UserRepository;
import org.h2.tools.Server;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@TestConfiguration
public class PersistenceContext {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private MealRepository mealRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    AccountController accountController;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }



    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort","9064");
    }

//    @Bean
//    public WebTestClient webTestClient(){
//        WebTestClient client = MockMvcWebTestClient.bindToController(accountController)
//                .configureClient()
//                .baseUrl("localhost:3306")
//                .build();
//        return client;
//    }
}
