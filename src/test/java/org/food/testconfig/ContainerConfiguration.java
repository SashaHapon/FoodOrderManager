package org.food.testconfig;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainerConfiguration {
    @Bean
    @Profile("mysql")
    @ServiceConnection
    public JdbcDatabaseContainer<?> mysqlContainer() {
        return new MySQLContainer<>("mysql:latest").withUsername("root").withDatabaseName("mydb");
    }

    @Bean
    @Profile("postgres")
    @ServiceConnection
    public JdbcDatabaseContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:latest").withUsername("root");
    }
}
