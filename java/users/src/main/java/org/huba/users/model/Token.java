package org.huba.users.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Token {
    @Id
    private UUID id;

    private String value;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private Date createdDate;
    private Date expiredDate;
    @ManyToOne
    private User user;

    @PrePersist
    public void init() {
        id = UUID.randomUUID();
    }
}
