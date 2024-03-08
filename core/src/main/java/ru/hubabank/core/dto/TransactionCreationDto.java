package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TransactionCreationDto {

    @Schema(description = "Изменение баланса счета в копейках")
    private long balanceChange;
}
