package org.huba.users.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Privilege {
    @Id
    private String name;
    private String description;

    @Override
    public String toString() {
        return name;
    }

    public String toClaim() {
        return name;
    }
}
