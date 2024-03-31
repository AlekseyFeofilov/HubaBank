package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class TransferDto {

    @Schema(description = "Идентификатор перевода")
    private UUID id;

    @Schema(description = "Счет, с которого списаны деньги")
    private BillInfoDto source;

    @Schema(description = "Счет, на который поступили деньги")
    private BillInfoDto target;

    @Schema(description = "Изменение баланса счета в копейках")
    private long amount;

    @Schema(description = "Момент времени выполнения транзакции")
    private Instant instant;
}
