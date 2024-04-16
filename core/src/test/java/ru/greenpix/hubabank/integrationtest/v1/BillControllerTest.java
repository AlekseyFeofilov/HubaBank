package ru.greenpix.hubabank.integrationtest.v1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.greenpix.hubabank.integrationtest.AbstractIntegrationTest;
import ru.greenpix.hubabank.provider.AllVersionExcludeV3ArgumentsProvider;
import ru.greenpix.hubabank.provider.V1PathArgumentsProvider;
import ru.hubabank.core.HubabankCoreApplication;
import ru.hubabank.core.dto.ClientBillDtoV1;
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

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешный просмотр всех счетов")
    @Sql("/sql/insert-client-bill-collection.sql")
    void whenGetAllBillsThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/bills", versionPath))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/bill-list-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешный просмотр всех счетов без прав")
    void whenGetAllBillsThenForbiddenIfHaveNotPrivilege(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/bills", versionPath))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешный просмотр всех своих счетов")
    @Sql("/sql/insert-client-bill-collection.sql")
    void whenGetBillsThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills", versionPath, CLIENT_USER_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/client-bill-list-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешный просмотр всех счетов другого клиента")
    @Sql("/sql/insert-client-bill-collection.sql")
    void whenGetBillsOtherClientThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills", versionPath, CLIENT_USER_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/client-bill-list-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешный просмотр всех счетов другого клиента без прав")
    void whenGetBillsOtherClientThenForbidden(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills", versionPath, EMPLOYER_USER_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешный просмотр своего счета")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillDetailsThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/client-bill-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешный просмотр счета другого клиента")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillDetailsOtherClientThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/client-bill-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешное просмотр несуществующего счета")
    void whenGetBillDetailsThenNotFoundIfNotExists(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Неуспешный просмотр информации о закрытом счете")
    @Sql("/sql/insert-client-closed-bill.sql")
    void whenGetBillDetailsThenNotFoundIfBillIsClosed(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Неуспешное просмотр счета, который не принадлежит указанному клиенту")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillDetailsThenNotFoundIfBelongsOtherClient(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s", versionPath, EMPLOYER_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешный просмотр счета другого клиента без прав")
    void whenGetBillDetailsOtherClientThenForbidden(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s", versionPath, EMPLOYER_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Успешное создание своего счета")
    void whenCreateBillThenSuccess(String versionPath) throws Exception {
        assertThat(billRepository.count()).isZero();

        MvcResult result = mockMvc.perform(post(buildUrl("%s/users/%s/bills", versionPath, CLIENT_USER_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        ClientBillDtoV1 dto = getContentAsObject(result, ClientBillDtoV1.class);
        assertThat(dto.getBalance()).isZero();

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(1);

        Bill bill = bills.iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getBalance()).isZero();
        assertThat(bill.getCreationInstant()).isNotNull();
        assertThat(bill.getClosingInstant()).isNull();
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Успешное создание счета для другого клиента")
    void whenCreateBillOtherClientThenSuccess(String versionPath) throws Exception {
        assertThat(billRepository.count()).isZero();

        MvcResult result = mockMvc.perform(post(buildUrl("%s/users/%s/bills", versionPath, CLIENT_USER_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        ClientBillDtoV1 dto = getContentAsObject(result, ClientBillDtoV1.class);
        assertThat(dto.getBalance()).isZero();

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(1);

        Bill bill = bills.iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getBalance()).isZero();
        assertThat(bill.getCreationInstant()).isNotNull();
        assertThat(bill.getClosingInstant()).isNull();
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешное создание счета для другого клиента без прав")
    void whenCreateBillOtherClientThenForbiddenIfHaveNotPrivilege(String versionPath) throws Exception {
        long count = billRepository.count();

        mockMvc.perform(post(buildUrl("%s/users/%s/bills", versionPath, EMPLOYER_USER_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());

        assertThat(billRepository.count()).isEqualTo(count);
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешное закрытие своего счета")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(delete(buildUrl("%s/users/%s/bills/%s", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk());

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNotNull();
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешное закрытие счета у другого клиента")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillOtherClientThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(delete(buildUrl("%s/users/%s/bills/%s", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(1);

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNotNull();
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Неуспешное закрытие несуществующего счета")
    void whenCloseBillThenNotFoundIfNotExists(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(delete(buildUrl("%s/users/%s/bills/%s", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorDto actual = getContentAsObject(result, ErrorDto.class);
        assertThat(actual.getCode()).isEqualTo(ErrorType.BILL_NOT_FOUND.getCode());
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Неуспешное закрытие счета, который не принадлежит указанному клиенту")
    @Sql("/sql/insert-client-bill.sql")
    void whenCloseBillThenNotFoundIfBelongsOtherClient(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(delete(buildUrl("%s/users/%s/bills/%s", versionPath, EMPLOYER_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNull();
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Неуспешное закрытие счета с положительным балансом")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenCloseBillThenBadRequestIfBalanceIsPositive(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(delete(buildUrl("%s/users/%s/bills/%s", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertError(result, ErrorType.CLOSING_BILL_WITH_POSITIVE_BALANCE);

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getUserId()).isEqualTo(UUID.fromString(CLIENT_USER_ID));
        assertThat(bill.getClosingInstant()).isNull();
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешное закрытие счета у другого клиента без прав")
    void whenCloseBillOtherClientThenForbiddenIfHaveNotPrivilege(String versionPath) throws Exception {
        mockMvc.perform(delete(buildUrl("%s/users/%s/bills/%s", versionPath, EMPLOYER_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @AfterEach
    @Transactional
    void truncateTables() {
        billRepository.deleteAll();
    }
}
