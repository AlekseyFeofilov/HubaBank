package ru.hubabank.core.dto;

import lombok.Data;
import ru.hubabank.core.entity.TransactionReason;

import java.math.BigDecimal;

@Data
public class TransactionCreationDto {

    private BigDecimal balanceChange;
    private TransactionReason reason;
}
