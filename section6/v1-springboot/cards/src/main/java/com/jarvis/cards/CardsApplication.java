package com.jarvis.cards;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.jarvis.cards.controller") })
@EnableJpaRepositories("com.jarvis.cards.repository")
@EntityScan("com.jarvis.cards.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
    info = @Info(
        title = "Cards microservice REST API Documentation",
        description = "Banking Cards microservice REST API Documentation",
        version = "v1",
        contact = @Contact(
            name = "Madan Reddy",
            email = "tutor@jarvis.com",
            url = "https://www.jarvis.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.jarvis.com"
        )
    ),
    externalDocs = @ExternalDocumentation(
        description = "Banking Cards microservice REST API Documentation",
        url = "https://www.jarvis.com/swagger-ui.html"
    )
)
public class CardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardsApplication.class, args);
    }

}
