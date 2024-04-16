package ru.hubabank.core.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hubabank.core.versioning.ApiVersion;

@Configuration
public class OpenApiConfiguration extends AbstractOpenApiConfiguration {

    @Bean
    public GroupedOpenApi groupedOpenApiLatestVersion() {
        return createGroupedOpenApiBuilder(ApiVersion.MAX, "version-latest", false).build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV1() {
        return createGroupedOpenApiBuilder(ApiVersion.VERSION_1, true).build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV2() {
        return createGroupedOpenApiBuilder(ApiVersion.VERSION_2, false).build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV3() {
        return createGroupedOpenApiBuilder(ApiVersion.VERSION_3, false).build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiOldFormat() {
        String[] paths = {"/users/**", "/bills/**", "/internal/**"};
        return createGroupedOpenApiBuilder("v1", "old-format-version-001", paths, true)
                .build();
    }
}
