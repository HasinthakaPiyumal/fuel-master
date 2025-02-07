package com.uokse.fuelmaster.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Fuel Master API",
                version = "1.0",
                description = "Fuel Master API for Fuel Quota Management System",
                license = @License(
                        name = "MIT License",
                        url = "https://github.com/HasinthakaPiyumal/fuel-master/blob/main/LICENSE"
                )
        ),
        servers =
                {
                        @Server(url = "http://localhost:8080", description = "Local Server"),
                        @Server(url = "https://api-fuel-master-fbc37438737d.herokuapp.com", description = "Production Server")
                }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Bearer Token",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
