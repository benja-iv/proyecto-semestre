package com.smartfood.ms_clientes.config;

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
                        .title("ms-clientes API")
                        .version("1.0.0")
                        .description("API REST para gestión de clientes"))
                .addServersItem(new Server()
                        .url("http://localhost:9000")
                        .description("Servidor API Gateway"));
    }
}