package ru.hubabank.core.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.entity.Transaction;
import ru.hubabank.core.entity.TransactionReason;
import ru.hubabank.core.entity.Transfer;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.integration.service.CurrencyService;
import ru.hubabank.core.repository.BillRepository;
import ru.hubabank.core.repository.TransferRepository;
import ru.hubabank.core.service.strategy.SimpleBillSearchStrategy;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static ru.hubabank.core.constant.HeaderConstants.REQUEST_ID_HEADER;

@Service
@RequiredArgsConstructor
public class HttpLoggingService {

    public void handle(HttpServletRequest request, HttpServletResponse response, long nanosElapsed) {
        String httpMethod = request.getMethod();
        String path = request.getRequestURI();
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        int httpStatus = response.getStatus();
    }
}
