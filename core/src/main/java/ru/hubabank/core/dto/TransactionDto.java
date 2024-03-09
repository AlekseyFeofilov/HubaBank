package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.hubabank.core.entity.TransactionReason;

import java.time.Instant;
import java.util.UUID;

@Data
public class TransactionDto {

    @Schema(description = "Идентификатор транзакции")
    private UUID id;

    @Schema(description = "Идентификатор счета транзакции")
    private UUID billId;

    @Schema(description = "Изменение баланса счета в копейках")
    private long balanceChange;

    @Schema(description = "Причина выполнения транзакции")
    private TransactionReason reason;

    @Schema(description = "Момент времени выполнения транзакции")
    private Instant instant;
}
