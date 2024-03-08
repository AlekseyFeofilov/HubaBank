package org.huba.users.model;

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

    private String firstName;// Имя
    private String secondName; // Фамилия
    private String thirdName; // Отчество

    private String phone;

    private String passwordHash;

    @ManyToMany
    private Set<Privilege> additionPrivileges = new HashSet<>();

    @ManyToMany
    private Set<Privilege> blockedPrivileges = new HashSet<>();

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    private boolean employee;

    private Date blockedDate;
    @PrePersist
    public void init() {
        id = UUID.randomUUID();
    }

    public Set<Privilege> getPrivileges() {
        Set<Privilege> privileges = new HashSet<>();
        for(Privilege privilege : additionPrivileges) {
            privileges.add(privilege);
        }
        for(Role role : roles) {
            for(Privilege privilege : role.getPrivileges()) {
                privileges.add(privilege);
            }
        }
        for(Privilege privilege : blockedPrivileges) {
            privileges.remove(privilege);
        }
        return privileges;
    }
}
