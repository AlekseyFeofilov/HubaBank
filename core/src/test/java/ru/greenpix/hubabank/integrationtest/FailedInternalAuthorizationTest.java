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
import static ru.greenpix.hubabank.constants.TestConstants.AUTHORIZATION_HEADER;
import static ru.greenpix.hubabank.constants.TestConstants.BILL_ID;
import static ru.hubabank.core.constant.HeaderConstants.API_KEY_HEADER;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        classes = HubabankCoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class FailedInternalAuthorizationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Неуспешная авторизация, если API ключ не был отправлен")
    void whenRequestThenUnauthenticatedIfApiKeyIsNotSent() throws Exception {
        mockMvc.perform(get("/internal/bills/" + BILL_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Неуспешная авторизация, если отправлен неверный API ключ")
    void whenRequestThenUnauthenticatedIfTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/internal/bills/" + BILL_ID)
                        .header(API_KEY_HEADER, "invalid_api_key"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Неуспешная авторизация, если отправлен JWT токен вместо API ключа")
    void whenRequestThenUnauthenticatedIfUserIsJwtTokenSent() throws Exception {
        mockMvc.perform(get("/internal/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, "token_79009530902"))
                .andExpect(status().isUnauthorized());
    }
}