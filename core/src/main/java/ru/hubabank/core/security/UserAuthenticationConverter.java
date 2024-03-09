package ru.hubabank.core.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.hubabank.core.integration.object.UserInfo;
import ru.hubabank.core.integration.service.UserService;
import ru.hubabank.core.security.object.Privilege;
import ru.hubabank.core.security.object.Role;
import ru.hubabank.core.security.object.User;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserAuthenticationConverter implements AuthenticationConverter {

    private static final String AUTHORIZATION_SCHEME_BEARER = "Bearer";

    private final UserService userService;

    @Override
    public @Nullable Authentication convert(@NotNull HttpServletRequest request) {
        String token = extractToken(request);

        if (token == null) {
            return null;
        }

        try {
            UserInfo userInfo = userService.fetchUserInfo(token);

            if (userInfo.isBlocked()) {
                log.debug("User {} is blocked, access denied", token);
                return null;
            }

            User user = new User(userInfo.getId(), userInfo.getPhone());
            List<GrantedAuthority> authorities = createAuthorities(userInfo);
            return UsernamePasswordAuthenticationToken.authenticated(user, null, authorities);
        } catch (AuthenticationException e) {
            return null;
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }

        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHORIZATION_SCHEME_BEARER)) {
            return null;
        }
        if (header.equalsIgnoreCase(AUTHORIZATION_SCHEME_BEARER)) {
            throw new BadCredentialsException("Empty bearer authentication token");
        }
        return header;
    }

    private List<GrantedAuthority> createAuthorities(UserInfo userInfo) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(userInfo.getRoles()
                .stream()
                .map(Role::new)
                .toList());
        authorities.addAll(userInfo.getPrivileges()
                .stream()
                .map(Privilege::new)
                .toList());
        return authorities;
    }
}
