package ru.greenpix.hubabank.integrationtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.hubabank.core.HubabankCoreApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.greenpix.hubabank.constants.TestConstants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        classes = HubabankCoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
// TODO
class BillControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Успешное создание своего счета")
    void whenCreateBillThenSuccess() throws Exception {
        mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешное создание счета для другого клиента")
    void whenCreateBillOtherClientThenSuccess() throws Exception {
        mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Неуспешное создание счета для другого клиента без прав")
    void whenCreateBillOtherClientThenSuccessIfHaveNotPrivilege() throws Exception {
        mockMvc.perform(post("/users/" + EMPLOYER_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Успешное закрытие своего счета")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillThenSuccess() throws Exception {
        mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешное закрытие счета у другого клиента")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillOtherClientThenSuccess() throws Exception {
        mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Неуспешное закрытие несуществующего счета")
    void whenCloseBillThenNotFoundIfNotExists() throws Exception {
        mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Неуспешное закрытие счета, который не принадлежит указанному клиенту")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillThenNotFoundIfBelongsOtherClient() throws Exception {
        mockMvc.perform(delete("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Неуспешное закрытие счета с положительным балансом")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenCloseBillThenBadRequestIfBalanceIsPositive() throws Exception {
        mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Неуспешное закрытие счета с негативным балансом")
    @Sql("/sql/insert-client-bill-with-negative-balance.sql")
    void whenCloseBillThenBadRequestIfBalanceIsNegative() throws Exception {
        mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Неуспешное закрытие счета у другого клиента без прав")
    void whenCloseBillOtherClientThenUnauthorizedIfHaveNotPrivilege() throws Exception {
        mockMvc.perform(delete("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }
}
