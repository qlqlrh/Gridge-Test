package com.example.demo.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @Info(title = "demo 서비스 API 명세서",
                description = "스프링부트 demo 서비스 CRUD 실습 API 명세서",
                version = "v1"),
        security = @SecurityRequirement(name = "X-ACCESS-TOKEN"))
@SecurityScheme(
        name = "X-ACCESS-TOKEN",
        type = SecuritySchemeType.APIKEY,
        paramName = "X-ACCESS-TOKEN",
        in = SecuritySchemeIn.HEADER
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("demo 서비스 API v1")
                .pathsToMatch(paths)
                .build();
    }
}
