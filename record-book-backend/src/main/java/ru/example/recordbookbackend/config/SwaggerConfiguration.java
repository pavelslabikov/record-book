package ru.example.recordbookbackend.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {

    public static final String RESOURCES_BASE_PACKAGE = "ru.example.recordbookbackend.controller";

    public static final String PATH_REGEX = "/api/**";

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springshop-public")
                .pathsToMatch("/api/**")
                .build();
    }




}
