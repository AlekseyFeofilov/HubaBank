package ru.hubabank.core.dto;

import lombok.Data;
import ru.hubabank.core.entity.TransactionReason;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionDto {

    private UUID id;
    private UUID billId;
    private BigDecimal balanceChange;
    private TransactionReason reason;
}
