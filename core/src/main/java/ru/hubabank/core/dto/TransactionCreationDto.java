package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.hubabank.core.entity.TransactionReason;

@Data
public class TransactionCreationDto {

    @Schema(description = "Изменение баланса счета в копейках")
    private long balanceChange;

    @Schema(description = "Причина выполнения транзакции")
    private TransactionReason reason;
}
