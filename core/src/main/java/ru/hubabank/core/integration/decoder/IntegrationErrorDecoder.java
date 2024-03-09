package ru.hubabank.core.integration.decoder;

import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.hubabank.core.exception.IntegrationException;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static feign.Util.valuesOrEmpty;

@Component
@Slf4j
public class IntegrationErrorDecoder implements ErrorDecoder {

    @Override
    public @NotNull Exception decode(@NotNull String methodKey, @NotNull Response response) {
        if (log.isTraceEnabled()) {
             logResponse(methodKey, response);
        }
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        return new IntegrationException(responseStatus);
    }

    private void logResponse(String methodKey, Response response) {
        Request request = response.request();

        log.trace("""
                An occurred error while call {}
                ------------------------  Request   ------------------------
                {} {} {}
                
                Headers:
                {}
                Body:
                {}
                ------------------------  Response  ------------------------
                {} {}
                
                Headers:
                {}
                Body:
                {}
                ------------------------------------------------------------
                """,
                methodKey,
                request.httpMethod(), request.url(), request.protocolVersion(),
                convertHeadersToString(request.headers()),
                convertBodyToString(request),
                response.protocolVersion(), response.status(),
                convertHeadersToString(response.headers()),
                convertBodyToString(response));
    }

    private String convertHeadersToString(Map<String, Collection<String>> headers) {
        StringBuilder builder = new StringBuilder();
        for (String field : headers.keySet()) {
            for (String value : valuesOrEmpty(headers, field)) {
                builder.append(field).append(": ").append(value).append('\n');
            }
        }
        return builder.toString();
    }

    @SneakyThrows
    private String convertBodyToString(Request request) {
        return request.isBinary()
                ? "Binary data"
                : new String(request.body(), request.charset());
    }

    @SneakyThrows
    private String convertBodyToString(Response response) {
        try (BufferedReader reader = new BufferedReader(response.body().asReader(response.charset()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
