package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class BillDto {

    @Schema(description = "Идентификатор счета")
    private UUID id;

    @Schema(description = "Идентификатор клиента счета")
    private UUID userId;

    @Schema(description = "Баланс счета в копейках")
    private long balance;

    @Schema(description = "Флаг, указывающий на то, что счет закрыт")
    private boolean closed;
}
