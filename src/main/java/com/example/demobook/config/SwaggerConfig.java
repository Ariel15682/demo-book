package com.example.demobook.config;

import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Collections;

/**
 * Configuracion Swagger para la generacion de documentacion de la API REST
 * HTML http://localhost:8080/swagger-ui/#/
 * JSON http://localhost:8080/v2/api-docs
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        //builder: method chaining, concatenar llamadas
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiDetails(){
        return new ApiInfo("Spring Boot Book API REST",
                "Library Api rest docs",
                "1.0",
                "http://www.google.com",
                new Contact("Ariel_Diaz","http://www.google.com",
                        "arieldiaz@mail.com"),
                "MIT",
                "http://www.google.com",
                Collections.EMPTY_LIST);
    }
}
