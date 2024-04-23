package ru.hubabank.core.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.hubabank.core.constant.HeaderConstants;
import ru.hubabank.core.service.HttpLoggingService;
import ru.hubabank.core.versioning.ApiVersion;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(?<=/api/v)\\d+(?=/)");

    private final HttpLoggingService httpLoggingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestId = request.getHeader(HeaderConstants.REQUEST_ID_HEADER);

        if (request.getRequestURI().startsWith("/swagger-ui/")
                || request.getRequestURI().startsWith("/v3/api-docs")
                || getVersion(request) < ApiVersion.VERSION_3.getNumber()
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        if (requestId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.warn("В запросе {} {} отсутствует идентификатор запроса",
                    request.getMethod(), request.getRequestURI());
            return;
        }

        long start = System.nanoTime();
        filterChain.doFilter(request, response);
        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        httpLoggingService.handle(request, response, timeElapsed);
    }

    private int getVersion(HttpServletRequest request) {
        Matcher matcher = VERSION_PATTERN.matcher(request.getRequestURI());
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group());
            } catch (NumberFormatException ignored) {
                return ApiVersion.MIN.getNumber();
            }
        }
        return ApiVersion.MIN.getNumber();
    }
}
