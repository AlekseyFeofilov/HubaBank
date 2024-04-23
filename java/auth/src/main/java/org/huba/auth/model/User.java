package org.huba.auth.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_info")
public class User {
    @Id
    private UUID id;

    private String passwordHash;
    @PrePersist
    public void init() {
        id = UUID.randomUUID();
    }
}
