package ru.hubabank.core.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hubabank.core.versioning.ApiVersion;

import static ru.hubabank.core.constant.HeaderConstants.API_KEY_HEADER;
import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_INTERNAL_SCHEME;
import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_USER_SCHEME;

@Configuration
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
public class OpenApiConfiguration extends AbstractOpenApiConfiguration {

    @Bean
    public GroupedOpenApi groupedOpenApiLatestVersion() {
        return createGroupedOpenApiBuilder(ApiVersion.MAX, "version-latest").build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiVersion1() {
        return createGroupedOpenApiBuilder(ApiVersion.VERSION_1).build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiOldFormat() {
        String[] paths = {"/users/**", "/bills/**", "/internal/**"};
        return createGroupedOpenApiBuilder("v1", "old-format-version-001", paths)
                .build();
    }
}
