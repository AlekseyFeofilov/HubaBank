package ru.hubabank.core.integration.dto;

import lombok.Data;

@Data
public class CurrencyDto {

    private String code;
    private double value;
}