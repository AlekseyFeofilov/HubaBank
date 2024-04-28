package org.huba.users.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.huba.users.service.ErrorService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Component
@WebFilter(filterName = "LogFilter", urlPatterns = "/*")
@RequiredArgsConstructor
public class ErrorFilter extends OncePerRequestFilter {

    private final ErrorService errorService;

    private Set<String> strings = Set.of("/users/api/v1/errors", "/users/api/v1/auth_page");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!strings.contains(request.getRequestURI())) {
            try {
                errorService.generateError();
            } catch (RuntimeException e) {
                response.setStatus(500);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
