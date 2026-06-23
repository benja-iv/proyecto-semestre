package com.smartfood.ms_reportes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("ms-reportes API")
                        .version("1.0.0")
                        .description("API REST para analíticas y reportes"))
                .addServersItem(new Server()
                        .url("http://localhost:9000")
                        .description("Servidor API Gateway"));
    }
}