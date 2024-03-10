package ru.greenpix.hubabank.integrationtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
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

    @Test
    @DisplayName("Неуспешная авторизация, если токен не был отправлен")
    void whenRequestThenUnauthenticatedIfTokenIsNotSent() throws Exception {
        mockMvc.perform(get("/bills"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Неуспешная авторизация, если отправлен неверный токен")
    void whenRequestThenUnauthenticatedIfTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/bills")
                        .header(AUTHORIZATION_HEADER, "invalid_token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Неуспешная авторизация, если пользователь заблокирован")
    void whenRequestThenUnauthenticatedIfUserIsBlocked() throws Exception {
        mockMvc.perform(get("/bills")
                        .header(AUTHORIZATION_HEADER, "token_79009530903"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Неуспешная авторизация, если отправлен API ключ вместо JWT токена")
    void whenRequestThenUnauthenticatedIfApiKeySent() throws Exception {
        mockMvc.perform(get("/bills")
                        .header(API_KEY_HEADER, API_KEY))
                .andExpect(status().isUnauthorized());
    }
}
