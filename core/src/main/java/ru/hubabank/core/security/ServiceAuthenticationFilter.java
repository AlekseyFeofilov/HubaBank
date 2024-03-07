package ru.hubabank.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ServiceAuthenticationFilter extends OncePerRequestFilter {

    private final ServiceAuthenticationConverter converter;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Authentication authRequest = converter.convert(request);

            if (authRequest == null) {
                log.trace("Did not process authentication request since failed to find "
                        + "jwt token in Bearer Authorization header");
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }

            String username = authRequest.getName();
            log.trace("Found username {} in Bearer Authorization header", username);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authRequest);
            SecurityContextHolder.setContext(context);
        }
        catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            log.debug("Failed to process authentication request", ex);
        }

        filterChain.doFilter(request, response);
    }
}
