package ru.hubabank.core.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static ru.hubabank.core.constant.HeaderConstants.REQUEST_ID_HEADER;

@Slf4j
@Component
public class LoggingTimeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.nanoTime();
        filterChain.doFilter(request, response);
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        log.error("{} {} {} {} {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getParameterMap(),
                response.getStatus(),
                timeElapsed
        );
    }
}
