package ru.hubabank.core.configuration;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import ru.hubabank.core.versioning.ApiVersion;

import static ru.hubabank.core.constant.PathConstants.PREFIX_PATH;

abstract class AbstractOpenApiConfiguration {

    protected GroupedOpenApi.Builder createGroupedOpenApiBuilder(ApiVersion version) {
        String group = "version-" + String.format("%03d", version.getNumber());
        return createGroupedOpenApiBuilder(version, group);
    }

    protected GroupedOpenApi.Builder createGroupedOpenApiBuilder(ApiVersion version, String group) {
        String versionName = "v" + version.getNumber();
        String[] paths = {String.format("/%s/%s/**", PREFIX_PATH, versionName)};
        return createGroupedOpenApiBuilder("v" + version.getNumber(), group, paths);
    }

    protected GroupedOpenApi.Builder createGroupedOpenApiBuilder(String version, String group, String[] paths) {
        return GroupedOpenApi.builder()
                .group(group)
                .pathsToMatch(paths)
                .addOpenApiCustomizer(openApi -> openApi.setInfo(new Info()
                        .title("Core Service")
                        .version(version)
                        .description("Сервис для взаимодействия со счетами и транзакциями")
                ));
    }
}
