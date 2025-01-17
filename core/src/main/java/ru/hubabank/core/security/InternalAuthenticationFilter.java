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
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class InternalAuthenticationFilter extends OncePerRequestFilter {

    private final InternalAuthenticationConverter converter;
    private final RequestMatcher[] matchers;

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
                        + "api key in X-API-KEY header");
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }

            String username = authRequest.getName();
            log.trace("User {} authorized with api key in X-API-KEY header", username);

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

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return Stream.of(matchers)
                .map(NegatedRequestMatcher::new)
                .allMatch(matcher -> matcher.matches(request));
    }
}
