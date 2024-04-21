package ru.hubabank.core.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class IdempotentRequestId implements Serializable {

    private String method;

    private String request;

    private String idempotentKey;
}
