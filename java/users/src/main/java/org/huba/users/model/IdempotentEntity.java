package org.huba.users.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "idempotent")
public class IdempotentEntity {
    @Id
    private UUID requestId;
    private String body;
    private Integer status;
}
