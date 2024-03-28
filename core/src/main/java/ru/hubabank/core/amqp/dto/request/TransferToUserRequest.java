package ru.hubabank.core.amqp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class TransferToUserRequest {

    @Schema(description = "Идентификатор исходного счета, с которого будут переводиться деньги")
    private UUID sourceBillId;

    @Schema(description = "Идентификатор целевого пользователя, которому будут переводиться деньги")
    private UUID targetUserId;

    @Schema(description = "Изменение баланса счета в копейках")
    private long amount;
}
