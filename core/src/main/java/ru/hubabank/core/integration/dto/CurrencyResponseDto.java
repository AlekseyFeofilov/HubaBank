package ru.hubabank.core.integration.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyResponseDto {

    private Map<String, CurrencyDto> data;
}