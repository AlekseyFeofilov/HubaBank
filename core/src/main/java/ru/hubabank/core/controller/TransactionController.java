package ru.hubabank.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.hubabank.core.dto.TransactionCreationDto;
import ru.hubabank.core.dto.TransactionDto;
import ru.hubabank.core.entity.TransactionReason;
import ru.hubabank.core.service.TransactionService;
import ru.hubabank.core.service.strategy.UserBillSearchStrategy;
import ru.hubabank.core.versioning.ApiVersionRange;

import java.util.List;
import java.util.UUID;

import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_USER_SCHEME;
import static ru.hubabank.core.versioning.ApiVersion.*;

@SuppressWarnings("deprecation")
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("users/{userId}/bills/{billId}/transactions")
    @ApiVersionRange(min = MIN, max = VERSION_1)
    @PreAuthorize("(hasAuthority('PRIVILEGE_TRANSACTION_READ') and #userId == authentication.principal.id) " +
            "or hasAuthority('PRIVILEGE_TRANSACTION_READ_OTHERS')")
    @SecurityRequirement(name = SECURITY_USER_SCHEME)
    @Operation(
            summary = "Посмотреть историю транзакций по счету у пользователя",
            description = """
                    Описание прав:
                    * TRANSACTION_READ - доступ к просмотру истории транзакций своего счета
                    * TRANSACTION_READ_OTHERS - доступ к просмотру истории транзакций любого счета
                    """
    )
    @ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
    @ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")
    public List<TransactionDto> getTransactionsV1(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        return transactionService.getTransactions(userId, billId);
    }

    /**
     * @deprecated используйте {@link TransferController}
     */
    @Deprecated(since = "1.2.2")
    @GetMapping("users/{userId}/bills/{billId}/transactions")
    @ApiVersionRange(min = VERSION_2, max = MAX)
    @Operation(summary = "Посмотреть историю транзакций по счету у пользователя")
    public List<TransactionDto> getUserTransactionsV2(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        return transactionService.getTransactions(userId, billId);
    }

    /**
     * @deprecated используйте {@link TransferController}
     */
    @Deprecated(since = "1.2.2")
    @GetMapping("bills/{billId}/transactions")
    @ApiVersionRange(min = VERSION_2, max = MAX)
    @Operation(summary = "Посмотреть историю транзакций по счету у пользователя")
    public List<TransactionDto> getTransactionsV2(
            @PathVariable("billId") UUID billId
    ) {
        return transactionService.getTransactions(billId);
    }

    @PostMapping("users/{userId}/bills/{billId}/transactions")
    @ApiVersionRange(min = MIN, max = VERSION_1)
    @PreAuthorize("(hasAuthority('PRIVILEGE_TRANSACTION_WRITE') and #userId == authentication.principal.id) " +
            "or hasAuthority('PRIVILEGE_TRANSACTION_WRITE_OTHERS')")
    @SecurityRequirement(name = SECURITY_USER_SCHEME)
    @Operation(
            summary = "Выполнить транзакцию по счету у пользователя",
            description = """
                    Описание прав:
                    * TRANSACTION_WRITE - доступ к выполнению транзакции на своем счете
                    * TRANSACTION_WRITE_OTHERS - доступ к выполнению транзакции на любом счете
                    """
    )
    public void createTransactionV1(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId,
            @RequestBody TransactionCreationDto dto
    ) {
        transactionService.createTransaction(
                dto.getBalanceChange(),
                UserBillSearchStrategy.of(userId, billId),
                TransactionReason.TERMINAL
        );
    }
}
