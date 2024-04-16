package ru.greenpix.hubabank.integrationtest.v1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.greenpix.hubabank.integrationtest.AbstractIntegrationTest;
import ru.greenpix.hubabank.provider.AllVersionExcludeV3ArgumentsProvider;
import ru.greenpix.hubabank.provider.V1PathArgumentsProvider;
import ru.hubabank.core.HubabankCoreApplication;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.entity.Transaction;
import ru.hubabank.core.entity.TransactionReason;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.repository.BillRepository;
import ru.hubabank.core.repository.TransactionRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.greenpix.hubabank.constants.TestConstants.*;
import static ru.greenpix.hubabank.util.FileUtil.readFromFileToString;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        classes = HubabankCoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class TransactionControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешный просмотр истории транзакций по своему счету")
    @Sql({"/sql/insert-client-bill.sql", "/sql/insert-transactions.sql"})
    void whenGetTransactionHistoryThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/transaction-list-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Успешный просмотр истории транзакций по чужому счету")
    @Sql({"/sql/insert-client-bill.sql", "/sql/insert-transactions.sql"})
    void whenGetTransactionHistoryOtherClientThenSuccess(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/transaction-list-response.json")));
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Неуспешный просмотр истории транзакций по несуществующему счету")
    void whenGetTransactionHistoryThenNotFoundIfBillIsNotExists(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @ParameterizedTest
    @ArgumentsSource(AllVersionExcludeV3ArgumentsProvider.class)
    @DisplayName("Неуспешное просмотр истории транзакций по счету, который не принадлежит указанному клиенту")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetTransactionHistoryThenNotFoundIfBelongsOtherClient(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, EMPLOYER_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешный просмотр истории транзакций по своему счету без прав")
    void whenGetTransactionHistoryOtherClientThenForbiddenIfHaveNotPrivilege(String versionPath) throws Exception {
        mockMvc.perform(get(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, EMPLOYER_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Успешное зачисление денег на счет")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenDepositThenSuccess(String versionPath) throws Exception {
        assertThat(transactionRepository.count()).isZero();

        mockMvc.perform(post(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/deposit-transaction.json")))
                .andExpect(status().isOk());

        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions).hasSize(1);

        Transaction transaction = transactions.iterator().next();
        assertThat(transaction.getBill().getId()).isEqualTo(UUID.fromString(BILL_ID));
        assertThat(transaction.getBalanceChange()).isEqualTo(1);
        assertThat(transaction.getReason()).isEqualTo(TransactionReason.TERMINAL);
        assertThat(transaction.getInstant()).isNotNull();
        assertThat(transaction.getBill().getBalance()).isEqualTo(2);
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Успешное снятие денег со счета")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenWithdrawThenSuccess(String versionPath) throws Exception {
        assertThat(transactionRepository.count()).isZero();

        mockMvc.perform(post(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/withdrawal-transaction.json")))
                .andExpect(status().isOk());

        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions).hasSize(1);

        Transaction transaction = transactions.iterator().next();
        assertThat(transaction.getBill().getId()).isEqualTo(UUID.fromString(BILL_ID));
        assertThat(transaction.getBalanceChange()).isEqualTo(-1);
        assertThat(transaction.getReason()).isEqualTo(TransactionReason.TERMINAL);
        assertThat(transaction.getInstant()).isNotNull();
        assertThat(transaction.getBill().getBalance()).isZero();
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Успешное создание транзакции для чужого счета")
    @Sql("/sql/insert-client-bill.sql")
    void whenCreateTransactionOtherClientThenSuccess(String versionPath) throws Exception {
        assertThat(transactionRepository.count()).isZero();

        mockMvc.perform(post(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/deposit-transaction.json")))
                .andExpect(status().isOk());

        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions).hasSize(1);

        Transaction transaction = transactions.iterator().next();
        assertThat(transaction.getBill().getId()).isEqualTo(UUID.fromString(BILL_ID));
        assertThat(transaction.getBalanceChange()).isEqualTo(1);
        assertThat(transaction.getReason()).isEqualTo(TransactionReason.TERMINAL);
        assertThat(transaction.getInstant()).isNotNull();
        assertThat(transaction.getBill().getBalance()).isEqualTo(1);
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешное пополнение на несуществующий счет")
    void whenDepositThenNotFoundIfBillIsNotExists(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(post(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/withdrawal-transaction.json")))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);

        assertThat(transactionRepository.count()).isZero();
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешное пополнение на закрытый счет")
    @Sql("/sql/insert-client-closed-bill.sql")
    void whenDepositThenNotFoundIfBillIsClosed(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(post(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/withdrawal-transaction.json")))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);

        assertThat(transactionRepository.count()).isZero();

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getBalance()).isZero();
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешное создание транзакции с нулевой суммой изменения")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenCreateTransactionThenBadRequestIfBalanceChangeIsZero(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(post(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/invalid-transaction.json")))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertError(result, ErrorType.TRANSACTION_WITH_ZERO_BALANCE_CHANGE);

        assertThat(transactionRepository.count()).isZero();

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getBalance()).isEqualTo(1);
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешное снятие суммы больше, чем есть на счете")
    @Sql("/sql/insert-client-bill.sql")
    void whenWithdrawThenBadRequestIfBillBalanceIsLess(String versionPath) throws Exception {
        MvcResult result = mockMvc.perform(post(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, CLIENT_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/withdrawal-transaction.json")))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertError(result, ErrorType.CANNOT_NEGATIVE_BILL_BALANCE);

        assertThat(transactionRepository.count()).isZero();

        Bill bill = billRepository.findAll().iterator().next();
        assertThat(bill.getBalance()).isZero();
    }

    @ParameterizedTest
    @ArgumentsSource(V1PathArgumentsProvider.class)
    @DisplayName("Неуспешное создание транзакции для чужого счета без прав")
    void whenCreateTransactionOtherClientThenForbiddenIfHaveNotPrivilege(String versionPath) throws Exception {
        mockMvc.perform(post(buildUrl("%s/users/%s/bills/%s/transactions", versionPath, EMPLOYER_USER_ID, BILL_ID))
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/withdrawal-transaction.json")))
                .andExpect(status().isForbidden());
    }

    @AfterEach
    @Transactional
    void truncateTables() {
        transactionRepository.deleteAll();
        billRepository.deleteAll();
    }
}
