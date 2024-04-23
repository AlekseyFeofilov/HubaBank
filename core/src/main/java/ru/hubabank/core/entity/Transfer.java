package ru.hubabank.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transfer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "source_bill_id", nullable = false)
    private Bill source;

    @ManyToOne
    @JoinColumn(name = "target_bill_id", nullable = false)
    private Bill target;

    @Column(name = "deposit")
    private Long deposit;

    @Column(name = "withdrawal", nullable = false)
    private long withdrawal;

    @Column(name = "instant", nullable = false)
    private Instant instant;
}
