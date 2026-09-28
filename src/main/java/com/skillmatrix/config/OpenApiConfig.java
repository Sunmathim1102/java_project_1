package com.skillmatrix.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI skillMatrixOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SkillMatrix — Employee Skill Matrix & Project Staffing Recommender")
                        .description("REST API Backend for SkillMatrix system. Provides employee skill matrix management, " +
                                "project staffing recommendations with ranked scoring, team skill gap analysis, and capacity-constrained allocation management.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SkillMatrix Engineering Team")
                                .email("support@skillmatrix.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
