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

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class IdempotencyFilter extends OncePerRequestFilter {

    private final IdempotentRequestRepository idempotentRequestRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        if (!isIdempotentMethod(method)) {
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
            response.setStatus(HttpServletResponse.SC_CONFLICT);
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
}
