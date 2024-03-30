package ru.greenpix.hubabank.integrationtest;

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
import ru.greenpix.hubabank.provider.VersionPathArgumentsProvider;
import ru.hubabank.core.HubabankCoreApplication;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.repository.BillRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.greenpix.hubabank.constants.TestConstants.API_KEY;
import static ru.greenpix.hubabank.constants.TestConstants.BILL_ID;
import static ru.greenpix.hubabank.util.FileUtil.readFromFileToString;
import static ru.hubabank.core.constant.HeaderConstants.API_KEY_HEADER;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        classes = HubabankCoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class BillControllerInternalTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BillRepository billRepository;

    @ParameterizedTest
    @ArgumentsSource(VersionPathArgumentsProvider.class)
    @DisplayName("Успешный просмотр информации о счете")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetBillDetailsThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/internal/bills/%s", versionPath, BILL_ID))
                        .header(API_KEY_HEADER, API_KEY))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/bill-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(VersionPathArgumentsProvider.class)
    @DisplayName("Успешный просмотр информации о закрытом счете")
    @Sql("/sql/insert-client-closed-bill.sql")
    void whenGetBillDetailsThenSuccessIfBillIsClosed(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/internal/bills/%s", versionPath, BILL_ID))
                        .header(API_KEY_HEADER, API_KEY))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/closed-bill-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(VersionPathArgumentsProvider.class)
    @DisplayName("Неуспешный просмотр информации о несуществующем счете")
    void whenGetBillDetailsThenNotFoundIfBillIsNotExists(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(get(buildUrl("%s/internal/bills/%s", versionPath, BILL_ID))
                        .header(API_KEY_HEADER, API_KEY))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @AfterEach
    @Transactional
    void truncateTables() {
        billRepository.deleteAll();
    }
}
