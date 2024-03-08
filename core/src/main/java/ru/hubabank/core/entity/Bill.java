package ru.hubabank.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bill")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "balance", nullable = false)
    private long balance;

    @Column(name = "creation_instant", nullable = false)
    private Instant creationInstant;

    @Column(name = "closing_instant")
    private Instant closingInstant;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();
}