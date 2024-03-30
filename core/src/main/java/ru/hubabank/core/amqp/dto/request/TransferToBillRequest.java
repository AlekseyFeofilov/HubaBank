package ru.hubabank.core.amqp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class TransferToBillRequest {

    @Schema(description = "Идентификатор исходного счета, с которого будут переводиться деньги")
    private UUID sourceBillId;

    @Schema(description = "Идентификатор целевого счета, на который будут переводиться деньги")
    private UUID targetBillId;

    @Schema(description = "Изменение баланса счета в копейках")
    private long amount;
}
