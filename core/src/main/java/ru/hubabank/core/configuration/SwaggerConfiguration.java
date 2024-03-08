package ru.hubabank.core.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static ru.hubabank.core.constant.HeaderConstants.API_KEY_HEADER;
import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_INTERNAL_SCHEME;
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
        scheme = "bearer",
        description = "Авторизация через JWT токен для пользователей"
)
@SecurityScheme(
        name = SECURITY_INTERNAL_SCHEME,
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = API_KEY_HEADER,
        description = "Авторизация через API ключ для внутренних микросервисов"
)
public class SwaggerConfiguration {

}
