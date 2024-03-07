package ru.hubabank.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "balance_change", precision = 19, scale = 2, nullable = false)
    private BigDecimal balanceChange;

    @Column(name = "instant", nullable = false)
    private Instant instant;

    @Column(name = "reason", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionReason reason;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;
}
