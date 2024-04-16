package ru.hubabank.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "property")
@Getter
@NoArgsConstructor
public class Property {

    @Id
    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @Column
    private String value;
}
