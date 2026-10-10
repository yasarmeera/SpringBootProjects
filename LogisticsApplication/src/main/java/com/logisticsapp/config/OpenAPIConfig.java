package com.logisticsapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// config - Swagger / OpenAPI settings
@Configuration
public class OpenAPIConfig {

    // Swagger title, description and the Basic auth (Authorize) button
    @Bean
    public OpenAPI logisticsOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Logistics API").version("1.0")
                .description("Customers, warehouses, inventory, shipments, tracking and delivery"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new Components().addSecuritySchemes("basicAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
    }
}