package ru.hubabank.core.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_USER_SCHEME;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Core Service",
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = SECURITY_USER_SCHEME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfiguration {

}
