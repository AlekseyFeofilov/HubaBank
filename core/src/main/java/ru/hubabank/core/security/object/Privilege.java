package ru.hubabank.core.security.object;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Locale;

@Data
public class Privilege implements GrantedAuthority {

    private final String name;

    public String getAuthority() {
        return "PRIVILEGE_" + name.toUpperCase(Locale.ROOT);
    }
}
