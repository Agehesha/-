package ua.opnu.labwork2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sportFieldBookingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Система бронювання спортивних майданчиків")
                        .version("1.0.0")
                        .description("Backend-сервіс для управління системою бронювання спортивних майданчиків. "
                                + "API дозволяє керувати користувачами, спортивними центрами, майданчиками, "
                                + "типами майданчиків та бронюваннями. Сервіс реалізовано на Spring Boot, "
                                + "використовує REST API, DTO, Jakarta Validation, централізовану обробку "
                                + "помилок, JPA/Hibernate та PostgreSQL.")
                        .contact(new Contact()
                                .name("Кафедра інформаційних систем, НУ «Одеська політехніка»")
                                .email("romashugaev99@gmail.com")));
    }
}
