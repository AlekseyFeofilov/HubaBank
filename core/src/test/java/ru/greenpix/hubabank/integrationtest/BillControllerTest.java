package ru.greenpix.hubabank.integrationtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.HubabankCoreApplication;
import ru.hubabank.core.dto.ClientBillDto;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.error.ErrorDto;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.repository.BillRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.greenpix.hubabank.constants.TestConstants.*;
import static ru.greenpix.hubabank.util.FileUtil.readFromFileToString;
import static ru.greenpix.hubabank.util.ResponseUtil.getContentAsObject;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        classes = HubabankCoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class BillControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BillRepository billRepository;

    @Test
    @DisplayName("Успешный просмотр всех счетов")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetAllBillsThenSuccess() throws Exception {
        mockMvc.perform(get("/bills")
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/bill-list-response.json")));
    }

    @Test
    @DisplayName("Неуспешный просмотр всех счетов без прав")
    void whenGetAllBillsThenForbiddenIfHaveNotPrivilege() throws Exception {
        mockMvc.perform(get("/bills")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Успешный просмотр всех своих счетов")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillsThenSuccess() throws Exception {
        mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/client-bill-list-response.json")));
    }

    @Test
    @DisplayName("Успешный просмотр всех счетов другого клиента")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillsOtherClientThenSuccess() throws Exception {
        mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/client-bill-list-response.json")));
    }

    @Test
    @DisplayName("Неуспешный просмотр всех счетов другого клиента без прав")
    void whenGetBillsOtherClientThenForbidden() throws Exception {
        mockMvc.perform(get("/users/" + EMPLOYER_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Успешный просмотр своего счета")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillDetailsThenSuccess() throws Exception {
        mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/client-bill-response.json")));
    }

    @Test
    @DisplayName("Успешный просмотр счета другого клиента")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillDetailsOtherClientThenSuccess() throws Exception {
        mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/client-bill-response.json")));
    }

    @Test
    @DisplayName("Неуспешное просмотр несуществующего счета")
    void whenGetBillDetailsThenNotFoundIfNotExists() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @Test
    @DisplayName("Неуспешный просмотр информации о закрытом счете")
    @Sql("/sql/insert-client-closed-bill.sql")
    void whenGetBillDetailsThenNotFoundIfBillIsClosed() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @Test
    @DisplayName("Неуспешное просмотр счета, который не принадлежит указанному клиенту")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillDetailsThenNotFoundIfBelongsOtherClient() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @Test
    @DisplayName("Неуспешный просмотр счета другого клиента без прав")
    void whenGetBillDetailsOtherClientThenForbidden() throws Exception {
        mockMvc.perform(get("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Успешное создание своего счета")
    void whenCreateBillThenSuccess() throws Exception {
        assertThat(billRepository.count()).isZero();

        MvcResult result = mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        ClientBillDto dto = getContentAsObject(result, ClientBillDto.class);
        assertThat(dto.getBalance()).isZero();

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(1);

        Bill bill = bills.iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getBalance()).isZero();
        assertThat(bill.getCreationInstant()).isNotNull();
        assertThat(bill.getClosingInstant()).isNull();
    }

    @Test
    @DisplayName("Успешное создание счета для другого клиента")
    void whenCreateBillOtherClientThenSuccess() throws Exception {
        assertThat(billRepository.count()).isZero();

        MvcResult result = mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        ClientBillDto dto = getContentAsObject(result, ClientBillDto.class);
        assertThat(dto.getBalance()).isZero();

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(1);

        Bill bill = bills.iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getBalance()).isZero();
        assertThat(bill.getCreationInstant()).isNotNull();
        assertThat(bill.getClosingInstant()).isNull();
    }

    @Test
    @DisplayName("Неуспешное создание счета для другого клиента без прав")
    void whenCreateBillOtherClientThenForbiddenIfHaveNotPrivilege() throws Exception {
        mockMvc.perform(post("/users/" + EMPLOYER_USER_ID + "/bills")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());

        assertThat(billRepository.count()).isZero();
    }

    @Test
    @DisplayName("Успешное закрытие своего счета")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillThenSuccess() throws Exception {
        mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk());

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNotNull();
    }

    @Test
    @DisplayName("Успешное закрытие счета у другого клиента")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillOtherClientThenSuccess() throws Exception {
        mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(1);

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNotNull();
    }

    @Test
    @DisplayName("Неуспешное закрытие несуществующего счета")
    void whenCloseBillThenNotFoundIfNotExists() throws Exception {
        MvcResult result = mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorDto actual = getContentAsObject(result, ErrorDto.class);
        assertThat(actual.getCode()).isEqualTo(ErrorType.BILL_NOT_FOUND.getCode());
    }

    @Test
    @DisplayName("Неуспешное закрытие счета, который не принадлежит указанному клиенту")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillThenNotFoundIfBelongsOtherClient() throws Exception {
        MvcResult result = mockMvc.perform(delete("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNull();
    }

    @Test
    @DisplayName("Неуспешное закрытие счета с положительным балансом")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenCloseBillThenBadRequestIfBalanceIsPositive() throws Exception {
        MvcResult result = mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertError(result, ErrorType.CLOSING_BILL_WITH_POSITIVE_BALANCE);

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNull();
    }

    @Test
    @DisplayName("Неуспешное закрытие счета с негативным балансом")
    @Sql("/sql/insert-client-bill-with-negative-balance.sql")
    void whenCloseBillThenBadRequestIfBalanceIsNegative() throws Exception {
        MvcResult result = mockMvc.perform(delete("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertError(result, ErrorType.CLOSING_BILL_WITH_NEGATIVE_BALANCE);

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNull();
    }

    @Test
    @DisplayName("Неуспешное закрытие счета у другого клиента без прав")
    void whenCloseBillOtherClientThenForbiddenIfHaveNotPrivilege() throws Exception {
        mockMvc.perform(delete("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID)
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @AfterEach
    @Transactional
    void truncateTables() {
        billRepository.deleteAll();
    }
}
