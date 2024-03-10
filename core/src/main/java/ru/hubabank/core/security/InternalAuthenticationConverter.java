package ru.hubabank.core.security;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;
import ru.hubabank.core.security.object.Role;
import ru.hubabank.core.security.object.SuperUser;

import java.util.Collection;
import java.util.Collections;

import static ru.hubabank.core.constant.HeaderConstants.API_KEY_HEADER;

@Component
public class InternalAuthenticationConverter implements AuthenticationConverter {

    private final String apiKey;

    public InternalAuthenticationConverter(@Value("${api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public @Nullable Authentication convert(@NotNull HttpServletRequest request) {
        if (!this.apiKey.equals(request.getHeader(API_KEY_HEADER))) {
            return null;
        }

        Collection<GrantedAuthority> authorities = Collections.singleton(new Role("internal"));
        return UsernamePasswordAuthenticationToken.authenticated(new SuperUser(), null, authorities);
    }
}
