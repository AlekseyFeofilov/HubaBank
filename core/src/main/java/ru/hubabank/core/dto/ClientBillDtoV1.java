package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class ClientBillDtoV1 {

    @Schema(description = "Идентификатор счета")
    private UUID id;

    @Schema(description = "Баланс счета в копейках")
    private long balance;
}
