package ru.hubabank.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.hubabank.core.dto.TransactionCreationDto;
import ru.hubabank.core.dto.TransactionDto;
import ru.hubabank.core.service.TransactionService;

import java.util.List;
import java.util.UUID;

import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_SCHEME_NAME;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("users/{userId}/bills/{billId}/transactions")
    @PreAuthorize("(hasAuthority('PRIVILEGE_TRANSACTION_READ') and #userId == authentication.principal.id) " +
            "or hasAuthority('PRIVILEGE_TRANSACTION_READ_OTHERS')")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    @Operation(
            summary = "Посмотреть историю транзакций по счету у пользователя",
            description = """
                    Описание прав:
                    * TRANSACTION_READ - доступ к просмотру истории транзакций своего счета
                    * TRANSACTION_READ_OTHERS - доступ к просмотру истории транзакций любого счета
                    """
    )
    public List<TransactionDto> getTransactions(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        return transactionService.getTransactions(userId, billId);
    }

    @PostMapping("users/{userId}/bills/{billId}/transactions")
    @PreAuthorize("(hasAuthority('PRIVILEGE_TRANSACTION_WRITE') and #userId == authentication.principal.id) " +
            "or hasAuthority('PRIVILEGE_TRANSACTION_WRITE_OTHERS')")
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    @Operation(
            summary = "Выполнить транзакцию по счету у пользователя",
            description = """
                    Описание прав:
                    * TRANSACTION_WRITE - доступ к выполнению транзакции на своем счете
                    * TRANSACTION_WRITE_OTHERS - доступ к выполнению транзакции на любом счете
                    """
    )
    public void createTransaction(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId,
            @RequestBody TransactionCreationDto dto
    ) {
        transactionService.createTransaction(userId, billId, dto);
    }
}
