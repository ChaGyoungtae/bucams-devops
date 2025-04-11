package com.bucams.bucams.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server().url("https://bucams-api.com:30804");
        return new OpenAPI()
            .addServersItem(server)
            .info(new Info()
                .title("API Documentation")
                .version("1.0")
                .description("API 명세서"));
    }
}
