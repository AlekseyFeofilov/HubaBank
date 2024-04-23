package ru.hubabank.core.integration.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.exception.IntegrationException;
import ru.hubabank.core.integration.client.LoggerClient;
import ru.hubabank.core.integration.dto.LogDto;

@Service
@RequiredArgsConstructor
public class LoggerService {

    private final LoggerClient loggerClient;

    public void sendLog(@NotNull LogDto body) {
        try {
            loggerClient.sendLog(body);
        } catch (IntegrationException e) {
            throw ErrorType.BAD_GATEWAY.createException(e);
        }
    }
}
