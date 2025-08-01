package com.ureca.ocean.jjh.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "User API", version = "v1"),
        servers = {
                @Server(url = "http://15.164.81.45", description = "nginx address")
        },
        security = {
                @SecurityRequirement(name = "AuthorizationHeader")
        }
)
@SecurityScheme(
        name = "AuthorizationHeader",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization"
)
public class SwaggerConfig {
}

