package ru.hubabank.core.amqp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.hubabank.core.amqp.dto.TransferEntity;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class TransferResponse {

    @Schema(description = "Идентификатор перевода")
    private UUID id;

    @Schema(description = "Источник")
    private TransferEntity source;

    @Schema(description = "Цель")
    private TransferEntity target;

    @Schema(description = "Изменение баланса счета в копейках")
    private long amount;

    @Schema(description = "Момент времени выполнения транзакции")
    private Instant instant;
}
