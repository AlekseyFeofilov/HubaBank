package ru.hubabank.core.controller.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hubabank.core.dto.TransactionCreationDto;
import ru.hubabank.core.entity.TransactionReason;
import ru.hubabank.core.security.annotation.HasInternalRole;
import ru.hubabank.core.service.TransactionService;
import ru.hubabank.core.service.strategy.SimpleBillSearchStrategy;
import ru.hubabank.core.versioning.ApiVersionRange;

import java.util.UUID;

import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_INTERNAL_SCHEME;
import static ru.hubabank.core.versioning.ApiVersion.MIN;
import static ru.hubabank.core.versioning.ApiVersion.VERSION_1;

@SuppressWarnings("deprecation")
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
@Tag(name = "internal",
        description = "Методы, доступные для внутренних сервисов"
)
public class TransactionInternalController {

    private final TransactionService transactionService;

    @PostMapping("bills/{billId}/transactions")
    @ApiVersionRange(min = MIN, max = VERSION_1)
    @HasInternalRole
    @SecurityRequirement(name = SECURITY_INTERNAL_SCHEME)
    @Operation(
            summary = "Выполнить транзакцию по счету у пользователя",
            description = "Этот метод предназначен для внутреннего сервиса 'Кредиты'"
    )
    @ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
    @ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")
    public void createTransaction(
            @PathVariable("billId") UUID billId,
            @RequestBody TransactionCreationDto dto
    ) {
        transactionService.createTransaction(
                dto.getBalanceChange(),
                SimpleBillSearchStrategy.of(billId),
                TransactionReason.LOAN
        );
    }
}
