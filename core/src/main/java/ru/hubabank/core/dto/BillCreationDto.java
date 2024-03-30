package ru.hubabank.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.hubabank.core.entity.CurrencyType;

@Data
public class BillCreationDto {

    @Schema(description = "Тип валюты счета")
    private CurrencyType currency;
}
