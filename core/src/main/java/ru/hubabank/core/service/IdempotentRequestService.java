package ru.hubabank.core.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.hubabank.core.entity.IdempotentRequest;
import ru.hubabank.core.entity.IdempotentRequestId;
import ru.hubabank.core.repository.IdempotentRequestRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class IdempotentRequestService {

    private final IdempotentRequestRepository idempotentRequestRepository;

    public void processRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            String idempotentKey
    ) throws ServletException, IOException {
        IdempotentRequestId id = IdempotentRequestId.builder()
                .method(request.getMethod())
                .request(request.getRequestURI())
                .idempotentKey(idempotentKey)
                .build();

        Optional<IdempotentRequest> runningIdempotentRequest = idempotentRequestRepository.findById(id);

        if (runningIdempotentRequest.isPresent()) {
            if (runningIdempotentRequest.get().isDone()) {
                response.setStatus(HttpServletResponse.SC_OK);
                log.warn("Запрос {} {} уже был выполнен с ключом идемпотентности {}",
                        request.getMethod(), request.getRequestURI(), idempotentKey);
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                log.warn("Запрос {} {} ещё выполняется с ключом идемпотентности {}",
                        request.getMethod(), request.getRequestURI(), idempotentKey);
            }
            return;
        }

        IdempotentRequest idempotentRequest = IdempotentRequest.builder()
                .method(request.getMethod())
                .request(request.getRequestURI())
                .idempotentKey(idempotentKey)
                .done(false)
                .build();

        idempotentRequestRepository.save(idempotentRequest);

        filterChain.doFilter(request, response);

        HttpStatus status = HttpStatus.valueOf(response.getStatus());

        if (status.is2xxSuccessful()) {
            idempotentRequest.setDone(true);
            idempotentRequestRepository.save(idempotentRequest);
        } else {
            idempotentRequestRepository.delete(idempotentRequest);
        }
    }
}
