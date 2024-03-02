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
    private Boolean admin;

    @Override
    public String toString() {
        return name + " " + admin + "\n";
    }

    public String toClaim() {
        return name;
    }
}
