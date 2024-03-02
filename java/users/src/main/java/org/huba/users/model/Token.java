package org.huba.users.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Token {
    @Id
    private UUID id;

    private String value;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private Timestamp createdTimestamp;
    private Timestamp expiredTimestamp;
}
