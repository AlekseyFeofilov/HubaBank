package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.hubabank.core.entity.CurrencyType;

import java.util.UUID;

@Data
public class ClientBillDtoV2 {

    @Schema(description = "Идентификатор счета")
    private UUID id;

    @Schema(description = "Баланс счета в копейках")
    private long balance;

    @Schema(description = "Тип валюты счета")
    private CurrencyType currency;
}
