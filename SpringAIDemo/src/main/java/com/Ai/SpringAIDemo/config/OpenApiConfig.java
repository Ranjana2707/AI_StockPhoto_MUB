package com.Ai.SpringAIDemo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration class for API documentation.
 * Configures API info, security schemes, and OpenAPI definition.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Chat Assistant API")
                        .version("1.0.0")
                        .description("REST API for AI Chat Assistant powered by Gemini. " +
                                "Supports chat interactions with AI models, conversation history management, " +
                                "and system prompt customization.")
                        .contact(new Contact()
                                .name("AI Chat Support")
                                .email("support@ai-chat.example.com")))
                .addSecurityItem(new SecurityRequirement().addList("apiKey"))
                .components(new Components()
                        .addSecuritySchemes("apiKey", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-API-Key")
                                .description("API key for authentication. " +
                                        "Add your API key in the header: X-API-Key: your-api-key")));
    }
}
