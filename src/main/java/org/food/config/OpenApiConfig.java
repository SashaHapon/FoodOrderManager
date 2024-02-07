package org.food.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Food order manager",
                description = "Loyalty System", version = "1.0.0",
                contact = @Contact(
                        name = "Alexandr Hapon",
                        email = "full8back@gmail.com"
                )
        )
)
public class OpenApiConfig {

}