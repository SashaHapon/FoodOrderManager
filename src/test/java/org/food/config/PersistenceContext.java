package org.food.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.api.repository.AccountRepository;
import org.food.api.repository.MealRepository;
import org.food.api.repository.OrderRepository;
import org.food.security.repository.RefreshTokenRepository;
import org.food.security.repository.UserRepository;
import org.food.security.service.UserService;
import org.h2.tools.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import java.sql.SQLException;

@Configuration
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
        return Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort","9092");
    }
}
