package ru.hubabank.core.amqp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.hubabank.core.amqp.dto.TransferEntity;
import ru.hubabank.core.error.ErrorType;

import java.time.Instant;

@Data
@Builder
public class TransferErrorResponse {

    @Schema(description = "Тип ошибки")
    private ErrorType errorType;

    @Schema(description = "Сообщение ошибки")
    private String errorMessage;

    @Schema(description = "Источник")
    private TransferEntity source;

    @Schema(description = "Цель")
    private TransferEntity target;

    @Schema(description = "Изменение баланса счета в копейках")
    private long amount;

    @Schema(description = "Момент времени выполнения транзакции")
    private Instant instant;
}
