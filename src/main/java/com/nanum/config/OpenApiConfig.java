package com.nanum.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String API_NAME = "Nanum Project API";
    private static final String API_DESCRIPTION = "Nanum Project Enroll Service API Documentation.";

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springDocVersion) {
        Info info = new Info()
                .title(API_NAME)
                .version(springDocVersion)
                .description(API_DESCRIPTION)
                .license(new License().name("Apache 2.0").url("<http://springdoc.org>"));

        return new OpenAPI().addServersItem(new Server().url("/enroll-service/"))
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(info);
    }
}
