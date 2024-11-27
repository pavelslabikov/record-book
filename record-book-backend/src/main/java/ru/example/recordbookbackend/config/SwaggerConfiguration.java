package ru.example.recordbookbackend.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {

    public static final String RESOURCES_BASE_PACKAGE = "ru.example.recordbookbackend.controller";

    public static final String PATH_REGEX = "/api/**";

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/v1/user*/**", "/api/v1/dean*/**", "/api/v1/student*/**", "/api/v1/teacher*/**")
                .build();
    }

    @Bean
    public GroupedOpenApi sheetApi() {
        return GroupedOpenApi.builder()
                .group("sheets")
                .pathsToMatch("/api/v1/sheet*/**")
                .build();
    }

    @Bean
    public GroupedOpenApi recordApi() {
        return GroupedOpenApi.builder()
                .group("record-books")
                .pathsToMatch("/api/v1/record-books/**")
                .build();
    }




}
