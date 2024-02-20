package org.food.testconfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainerConfiguration {

    @Autowired
    private Environment env;

    @Bean
    @ServiceConnection
    public JdbcDatabaseContainer<?> jdbcDatabaseContainer() {
        if (env.matchesProfiles("mysqltest")) {
            return new MySQLContainer<>("mysql:latest").withUsername("root").withDatabaseName("mydb");
        }
            return new PostgreSQLContainer<>("postgres:latest").withUsername("root");
    }
}
