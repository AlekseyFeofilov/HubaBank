package ru.hubabank.core.integration.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.hubabank.core.exception.IntegrationException;

@Component
public class IntegrationErrorDecoder implements ErrorDecoder {

    @Override
    public @NotNull Exception decode(@NotNull String methodKey, @NotNull Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        return new IntegrationException(responseStatus);
    }
}
