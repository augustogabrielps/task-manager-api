package com.augustogabriel.taskmanager.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Task Manager API",
                version = "1.0",
                description = "REST API for managing tasks with filtering, pagination and sorting",
                contact = @Contact(
                        name = "Augusto",
                        email = "deuvoid@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
