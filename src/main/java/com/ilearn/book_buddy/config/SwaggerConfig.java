package com.ilearn.book_buddy.config;

import com.ilearn.book_buddy.rest.response.ErrorResponse;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                contact = @Contact(
                        name = "Nouah Akoni",
                        email = "noahgreatakoni@gmail.com"
                ),
                title = "E-Book Store",
                description = "A book management system application",
                version = "0.0.1-SNAPSHOT"
        ),
        servers = {
                @Server(
                        description = "Development",
                        url ="http://localhost:8084"
                )
        },
         security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
        )
              @SecurityScheme(
                      name = "bearerAuth",
                      description = "JWT auth description",
                      scheme = "bearer",
                      type = SecuritySchemeType.HTTP,
                      bearerFormat = "JWT",
                      in = SecuritySchemeIn.HEADER
              )



public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer customOpenAPI() {
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(ErrorResponse.class));
        return openApi -> openApi
                .schema(resolvedSchema.schema.getName(),resolvedSchema.schema);
    }
}