package ru.hubabank.core.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.hubabank.core.constant.HeaderConstants;
import ru.hubabank.core.entity.IdempotentRequest;
import ru.hubabank.core.entity.IdempotentRequestId;
import ru.hubabank.core.repository.IdempotentRequestRepository;
import ru.hubabank.core.versioning.ApiVersion;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class IdempotencyFilter extends OncePerRequestFilter {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(?<=/api/v)\\d+(?=/)");

    private final IdempotentRequestRepository idempotentRequestRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        if (!isIdempotentMethod(method) || getVersion(request) < ApiVersion.VERSION_3.getNumber()) {
            filterChain.doFilter(request, response);
            return;
        }

        String idempotentKey = request.getHeader(HeaderConstants.IDEMPOTENT_KEY_HEADER);

        if (idempotentKey == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.warn("В запросе {} {} отсутствует ключ идемпотентности",
                    request.getMethod(), request.getRequestURI());
            return;
        }

        if (idempotentRequestRepository.existsById(IdempotentRequestId.builder()
                .method(request.getMethod())
                .request(request.getRequestURI())
                .idempotentKey(idempotentKey)
                .build())) {
            response.setStatus(HttpServletResponse.SC_OK);
            log.warn("Запрос {} {} уже был выполнен с ключом идемпотентности {}",
                    request.getMethod(), request.getRequestURI(), idempotentKey);
            return;
        }

        filterChain.doFilter(request, response);

        HttpStatus status = HttpStatus.valueOf(response.getStatus());

        if (status.is2xxSuccessful()) {
            idempotentRequestRepository.save(IdempotentRequest.builder()
                    .method(request.getMethod())
                    .request(request.getRequestURI())
                    .idempotentKey(idempotentKey)
                    .build());
        }
    }

    private boolean isIdempotentMethod(HttpMethod method) {
        return method == HttpMethod.POST ||
                method == HttpMethod.PUT ||
                method == HttpMethod.DELETE ||
                method == HttpMethod.PATCH;
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
