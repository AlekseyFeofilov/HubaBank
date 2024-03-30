package ru.greenpix.hubabank.integrationtest;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import ru.greenpix.hubabank.constants.TestConstants;
import ru.hubabank.core.error.ErrorDto;
import ru.hubabank.core.error.ErrorType;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.greenpix.hubabank.util.ResponseUtil.getContentAsObject;

public abstract class AbstractIntegrationTest {

    @SuppressWarnings("resource")
    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16.1-alpine3.19")
                    .withUsername("core")
                    .withPassword("core")
                    .withDatabaseName("core");

    @SuppressWarnings("resource")
    private static final GenericContainer<?> WIREMOCK =
            new GenericContainer<>("wiremock/wiremock:3.4.2-alpine")
                    .withCommand("--local-response-templating")
                    .withClasspathResourceMapping(
                            "wiremock",
                            "/home/wiremock",
                            BindMode.READ_ONLY
                    )
                    .withExposedPorts(8080);

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        Startables.deepStart(
                POSTGRES,
                WIREMOCK
        ).join();

        WireMock.configureFor(WIREMOCK.getHost(), WIREMOCK.getFirstMappedPort());

        registry.add("api-key", () -> TestConstants.API_KEY);
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.flyway.url", POSTGRES::getJdbcUrl);
        registry.add("spring.flyway.user", POSTGRES::getUsername);
        registry.add("spring.flyway.password", POSTGRES::getPassword);
        registry.add("spring.cloud.openfeign.client.config.users.url", AbstractIntegrationTest::getWireMockUrl);

        Awaitility.setDefaultTimeout(5, TimeUnit.SECONDS);
    }

    protected static String buildUrl(String url, Object... args) {
        return String.format(url, args);
    }

    protected static void assertError(MvcResult mvcResult, ErrorType errorType) throws IOException {
        ErrorDto actual = getContentAsObject(mvcResult, ErrorDto.class);
        assertThat(actual.getCode()).isEqualTo(errorType.getCode());
        assertThat(actual.getType()).isEqualTo(errorType);
    }

    private static String getWireMockUrl() {
        return "http://" + WIREMOCK.getHost() + ":" + WIREMOCK.getFirstMappedPort() + "/";
    }
}
