package ru.hubabank.core.integration.client;

import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hubabank.core.exception.IntegrationException;
import ru.hubabank.core.integration.dto.CurrencyResponseDto;
import ru.hubabank.core.integration.dto.LogDto;

@FeignClient("logger")
public interface LoggerClient {

    @PostMapping
    @NotNull
    CurrencyResponseDto sendLog(@RequestBody LogDto body) throws IntegrationException;
}
