package ru.hubabank.core.security.object;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Locale;

@Data
public class Role implements GrantedAuthority {

    private final String name;

    public String getAuthority() {
        return "ROLE_" + name.toUpperCase(Locale.ROOT);
    }
}
