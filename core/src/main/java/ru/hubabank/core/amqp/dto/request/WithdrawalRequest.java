package ru.hubabank.core.amqp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class WithdrawalRequest {

    @Schema(description = "Идентификатор счета, с которого будут сниматься деньги")
    private UUID billId;

    @Schema(description = "Изменение баланса счета в копейках")
    private long amount;
}
