package ru.hubabank.core.amqp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class DepositRequest {

    @Schema(description = "Идентификатор счета, на который будут переводиться деньги")
    private UUID billId;

    @Schema(description = "Изменение баланса счета в копейках")
    private long amount;
}
