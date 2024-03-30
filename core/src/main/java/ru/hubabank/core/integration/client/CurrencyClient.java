package ru.hubabank.core.integration.client;

import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hubabank.core.exception.IntegrationException;
import ru.hubabank.core.integration.dto.CurrencyResponseDto;

@FeignClient("currency")
public interface CurrencyClient {

    @GetMapping("/latest")
    @NotNull
    CurrencyResponseDto getLatest(
            @RequestHeader("apikey") String apiKey,
            @RequestParam("base_currency") String baseCurrency,
            @RequestParam("currencies") String currencies
    ) throws IntegrationException;
}
