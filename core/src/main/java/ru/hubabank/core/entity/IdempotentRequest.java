package ru.hubabank.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(IdempotentRequestId.class)
@Table(name = "idempotent_request")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdempotentRequest {

    @Id
    @Column(name = "method", nullable = false)
    private String method;

    @Id
    @Column(name = "request", nullable = false)
    private String request;

    @Id
    @Column(name = "idempotent_key", nullable = false)
    private String idempotentKey;
}
