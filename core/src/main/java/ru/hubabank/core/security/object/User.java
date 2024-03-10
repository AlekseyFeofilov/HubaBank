package ru.hubabank.core.security.object;

import lombok.Data;

import java.security.Principal;
import java.util.UUID;

@Data
public class User implements Principal {

    private final UUID id;
    private final String name;
}
