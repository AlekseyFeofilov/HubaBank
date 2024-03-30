package ru.greenpix.hubabank.integrationtest.v1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.greenpix.hubabank.integrationtest.AbstractIntegrationTest;
import ru.greenpix.hubabank.provider.V1PathArgumentsProvider;
import ru.hubabank.core.HubabankCoreApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.greenpix.hubabank.constants.TestConstants.API_KEY;
import static ru.greenpix.hubabank.constants.TestConstants.AUTHORIZATION_HEADER;
import static ru.hubabank.core.constant.HeaderConstants.API_KEY_HEADER;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        classes = HubabankCoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class FailedUserAuthorizationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешная авторизация, если токен не был отправлен")
    void whenRequestThenUnauthenticatedIfTokenIsNotSent(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/bills", versionPath)))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешная авторизация, если отправлен неверный токен")
    void whenRequestThenUnauthenticatedIfTokenIsInvalid(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/bills", versionPath))
                        .header(AUTHORIZATION_HEADER, "invalid_token"))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешная авторизация, если пользователь заблокирован")
    void whenRequestThenUnauthenticatedIfUserIsBlocked(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/bills", versionPath))
                        .header(AUTHORIZATION_HEADER, "token_79009530903"))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешная авторизация, если отправлен API ключ вместо JWT токена")
    void whenRequestThenUnauthenticatedIfApiKeySent(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/bills", versionPath))
                        .header(API_KEY_HEADER, API_KEY))
                .andExpect(status().isUnauthorized());
    }
}
