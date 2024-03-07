package ru.hubabank.core.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ClientBillDto {

    private UUID id;
    private BigDecimal balance;
    private boolean closed;
}
