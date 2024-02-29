package org.food;

import feign.Retryer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClientProperties;

@SpringBootApplication
@EnableFeignClients
public class Application {
    public static void main(String[] args) {
       // FeignClientProperties
        SpringApplication.run(Application.class, args);
    }

}
