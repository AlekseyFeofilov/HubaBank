package ru.greenpix.hubabank.integrationtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
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

    @Test
    @DisplayName("Успешный просмотр истории транзакций по своему счету")
    @Sql({"/sql/insert-client-bill.sql", "/sql/insert-transactions.sql"})
    void whenGetTransactionHistoryThenSuccess() throws Exception {
        mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/transaction-list-response.json")));
    }

    @Test
    @DisplayName("Успешный просмотр истории транзакций по чужому счету")
    @Sql({"/sql/insert-client-bill.sql", "/sql/insert-transactions.sql"})
    void whenGetTransactionHistoryOtherClientThenSuccess() throws Exception {
        mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(readFromFileToString("response/transaction-list-response.json")));
    }

    @Test
    @DisplayName("Неуспешный просмотр истории транзакций по несуществующему счету")
    void whenGetTransactionHistoryThenNotFoundIfBillIsNotExists() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @Test
    @DisplayName("Неуспешное просмотр истории транзакций по счету, который не принадлежит указанному клиенту")
    @Sql("/sql/insert-client-bill.sql")
    void whenGetTransactionHistoryThenNotFoundIfBelongsOtherClient() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID + "/transactions")
                        .header(AUTHORIZATION_HEADER, EMPLOYER_TOKEN))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);
    }

    @Test
    @DisplayName("Неуспешный просмотр истории транзакций по своему счету без прав")
    void whenGetTransactionHistoryOtherClientThenForbiddenIfHaveNotPrivilege() throws Exception {
        mockMvc.perform(get("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID + "/transactions")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Успешное зачисление денег на счет")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenDepositThenSuccess() throws Exception {
        assertThat(transactionRepository.count()).isZero();

        mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
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

    @Test
    @DisplayName("Успешное снятие денег со счета")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenWithdrawThenSuccess() throws Exception {
        assertThat(transactionRepository.count()).isZero();

        mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
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

    @Test
    @DisplayName("Успешное создание транзакции для чужого счета")
    @Sql("/sql/insert-client-bill.sql")
    void whenCreateTransactionOtherClientThenSuccess() throws Exception {
        assertThat(transactionRepository.count()).isZero();

        mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
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

    @Test
    @DisplayName("Неуспешное пополнение на несуществующий счет")
    void whenDepositThenNotFoundIfBillIsNotExists() throws Exception {
        MvcResult result = mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
                        .header(AUTHORIZATION_HEADER, CLIENT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFromFileToString("request/withdrawal-transaction.json")))
                .andExpect(status().isNotFound())
                .andReturn();

        assertError(result, ErrorType.BILL_NOT_FOUND);

        assertThat(transactionRepository.count()).isZero();
    }

    @Test
    @DisplayName("Неуспешное пополнение на закрытый счет")
    @Sql("/sql/insert-client-closed-bill.sql")
    void whenDepositThenNotFoundIfBillIsClosed() throws Exception {
        MvcResult result = mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
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

    @Test
    @DisplayName("Неуспешное создание транзакции с нулевой суммой изменения")
    @Sql("/sql/insert-client-bill-with-positive-balance.sql")
    void whenCreateTransactionThenBadRequestIfBalanceChangeIsZero() throws Exception {
        MvcResult result = mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
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

    @Test
    @DisplayName("Неуспешное снятие суммы больше, чем есть на счете")
    @Sql("/sql/insert-client-bill.sql")
    void whenWithdrawThenBadRequestIfBillBalanceIsLess() throws Exception {
        MvcResult result = mockMvc.perform(post("/users/" + CLIENT_USER_ID + "/bills/" + BILL_ID + "/transactions")
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

    @Test
    @DisplayName("Неуспешное создание транзакции для чужого счета без прав")
    void whenCreateTransactionOtherClientThenForbiddenIfHaveNotPrivilege() throws Exception {
        mockMvc.perform(post("/users/" + EMPLOYER_USER_ID + "/bills/" + BILL_ID + "/transactions")
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
