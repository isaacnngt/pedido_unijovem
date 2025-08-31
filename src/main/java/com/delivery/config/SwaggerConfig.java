package com.delivery.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Delivery API")
                        .version("1.0.0")
                        .description("API REST para gerenciar pedidos de delivery de forma simples e eficiente")
                        .contact(new Contact()
                                .name("Sistema Delivery")
                                .email("contato@delivery.com")
                                .url("https://github.com/isaacnngt/pedido_unijovem"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080" + contextPath)
                                .description("Servidor de Desenvolvimento Local"),
                        new Server()
                                .url("https://pedidounijovem-production.up.railway.app" + contextPath)
                                .description("Servidor de Produção (Railway)")
                ));
    }
}