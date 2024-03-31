package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.hubabank.core.entity.BillType;
import ru.hubabank.core.entity.CurrencyType;

import java.util.UUID;

@Data
public class BillDtoV2 {

    @Schema(description = "Идентификатор счета")
    private UUID id;

    @Schema(description = "Идентификатор клиента счета")
    private UUID userId;

    @Schema(description = "Баланс счета в копейках")
    private long balance;

    @Schema(description = "Тип счета")
    private BillType type;

    @Schema(description = "Тип валюты счета")
    private CurrencyType currency;

    @Schema(description = "Флаг, указывающий на то, что счет закрыт")
    private boolean closed;
}
