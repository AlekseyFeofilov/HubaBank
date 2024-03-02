package org.huba.users.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_info")
public class User {
    @Id
    private UUID id;

    private String firstName;// Имя
    private String secondName; // Фамилия
    private String thirdName; // Отчество

    private String phone;

    private String passwordHash;

    @ManyToMany
    private Set<Privilege> privileges;
    @PrePersist
    public void init() {
        id = UUID.randomUUID();
    }
}
