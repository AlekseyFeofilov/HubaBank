package ru.hubabank.core.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import ru.hubabank.core.versioning.ApiVersion;

import static ru.hubabank.core.constant.HeaderConstants.API_KEY_HEADER;
import static ru.hubabank.core.constant.PathConstants.PREFIX_PATH;
import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_INTERNAL_SCHEME;
import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_USER_SCHEME;

abstract class AbstractOpenApiConfiguration {

    protected GroupedOpenApi.Builder createGroupedOpenApiBuilder(ApiVersion version, boolean hasAuthorize) {
        String group = "version-" + String.format("%03d", version.getNumber());
        return createGroupedOpenApiBuilder(version, group, hasAuthorize);
    }

    protected GroupedOpenApi.Builder createGroupedOpenApiBuilder(ApiVersion version, String group, boolean hasAuthorize) {
        String versionName = "v" + version.getNumber();
        String[] paths = {String.format("/%s/%s/**", PREFIX_PATH, versionName)};
        return createGroupedOpenApiBuilder("v" + version.getNumber(), group, paths, hasAuthorize);
    }

    protected GroupedOpenApi.Builder createGroupedOpenApiBuilder(
            String version, String group, String[] paths, boolean hasAuthorize) {
        return GroupedOpenApi.builder()
                .group(group)
                .pathsToMatch(paths)
                .addOpenApiCustomizer(openApi -> {
                    openApi.setInfo(new Info()
                            .title("Core Service")
                            .version(version)
                            .description("Сервис для взаимодействия со счетами и транзакциями")
                    );
                    if (hasAuthorize) {
                        openApi.schemaRequirement(SECURITY_USER_SCHEME, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")
                                .description("Авторизация через JWT токен для пользователей")
                        );
                        openApi.schemaRequirement(SECURITY_INTERNAL_SCHEME, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(API_KEY_HEADER)
                                .description("Авторизация через API ключ для внутренних микросервисов")
                        );
                    }
                });
    }
}
