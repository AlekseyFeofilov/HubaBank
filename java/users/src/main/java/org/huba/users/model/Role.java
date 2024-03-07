package org.huba.users.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Role {
    @Id
    private String name;
    private String description;

    @ManyToMany
    private Set<Privilege> privileges;
}
