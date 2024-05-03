package ru.hubabank.core.amqp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.hubabank.core.entity.BillType;
import ru.hubabank.core.entity.CurrencyType;

import java.util.UUID;

@Data
@Builder
public class TransferEntity {

    @Schema(description = "Идентификатор пользователя")
    private UUID userId;

    @Schema(description = "Идентификатор счета")
    private UUID billId;

    @Schema(description = "Тип сущности")
    private BillType type;

    @Schema(description = "Изменение баланса счета в копейках")
    private long amount;

    @Schema(description = "Тип валюты счета")
    private CurrencyType currency;
}
