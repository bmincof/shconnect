package com.shinhan.connector.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {@Server(url = "http://52.79.227.171:8080/", description = "Default Server URL")},
        info = @Info(title = "SH-Connector API 명세서",
                description = "SH-Connector WebService API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/api/**"};

        return GroupedOpenApi.builder()
                .group("SH-Connector WebService API v1")
                .pathsToMatch(paths)
                .build();
    }

}