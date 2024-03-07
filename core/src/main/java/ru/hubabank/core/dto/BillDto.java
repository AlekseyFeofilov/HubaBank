package ru.hubabank.core.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BillDto {

    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private boolean closed;
}
