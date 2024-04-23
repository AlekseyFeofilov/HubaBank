package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.hubabank.core.entity.BillType;
import ru.hubabank.core.entity.CurrencyType;

import java.util.UUID;

@Data
@Builder
public class BillInfoDto {

    @Schema(description = "Идентификатор счета")
    private UUID billId;

    @Schema(description = "Идентификатор пользователя")
    private UUID userId;

    @Schema(description = "Тип сущности")
    private BillType type;

    @Schema(description = "Тип валюты счета")
    private CurrencyType currency;

    @Schema(description = "Изменение баланса счета в копейках", nullable = true)
    private Long amount;
}
