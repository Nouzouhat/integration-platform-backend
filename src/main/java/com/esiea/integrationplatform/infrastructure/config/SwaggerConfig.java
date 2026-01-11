package com.esiea.integrationplatform.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de Swagger/OpenAPI
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Plateforme Intégration ESIEA")
                        .version("1.0")
                        .description("API REST pour la gestion de la semaine d'intégration ESIEA. " +
                                "Cette API permet de gérer les événements et les inscriptions des étudiants.")
                        .contact(new Contact()
                                .name("Équipe Projet")
                                .email("contact@esiea.fr")));
    }
}