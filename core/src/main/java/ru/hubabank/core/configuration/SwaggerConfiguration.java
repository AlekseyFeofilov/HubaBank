package ru.hubabank.core.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_SCHEME_NAME;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Core Service"
        )
)
@SecurityScheme(
        name = SECURITY_SCHEME_NAME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfiguration {
}
