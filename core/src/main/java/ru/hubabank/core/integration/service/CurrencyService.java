package ru.hubabank.core.integration.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.exception.IntegrationException;
import ru.hubabank.core.integration.client.CurrencyClient;
import ru.hubabank.core.integration.dto.CurrencyResponseDto;

@Service
public class CurrencyService {

    private final CurrencyClient currencyClient;
    private final String apiKey;

    public CurrencyService(
            CurrencyClient currencyClient,
            @Value("${currency-api-key}") String apiKey
    ) {
        this.currencyClient = currencyClient;
        this.apiKey = apiKey;
    }

    public long convertCurrency(
            @NotNull String sourceCurrency,
            @NotNull String targetCurrency,
            long amount
    ) {
        try {
            CurrencyResponseDto response = currencyClient.getLatest(apiKey, sourceCurrency, targetCurrency);
            double value = response.getData().values().iterator().next().getValue();
            return (long) (amount * value); // TODO теряем копейки
        } catch (IntegrationException e) {
            throw ErrorType.BAD_GATEWAY.createException(e);
        }
    }
}
