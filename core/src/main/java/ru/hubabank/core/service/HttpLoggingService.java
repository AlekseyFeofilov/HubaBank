package ru.hubabank.core.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ru.hubabank.core.integration.dto.LogDto;
import ru.hubabank.core.integration.dto.LogRequestDto;
import ru.hubabank.core.integration.dto.LogResponseDto;
import ru.hubabank.core.integration.service.LoggerService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.UnaryOperator;

import static ru.hubabank.core.constant.HeaderConstants.REQUEST_ID_HEADER;

@Service
@RequiredArgsConstructor
public class HttpLoggingService {

    private final LoggerService loggerService;

    public void handle(HttpServletRequest request, HttpServletResponse response, long nanosElapsed) {
        String httpMethod = request.getMethod();
        String path = request.getRequestURI();
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        byte[] requestBody = new ContentCachingRequestWrapper(request).getContentAsByteArray();
        Map<String, String> requestHeaders = toHeaderMap(request.getHeaderNames().asIterator(), request::getHeader);

        int httpStatus = response.getStatus();
        Map<String, String> responseHeaders = toHeaderMap(response.getHeaderNames().iterator(), response::getHeader);
        byte[] responseBody = new ContentCachingResponseWrapper(response).getContentAsByteArray();

        loggerService.sendLog(LogDto.builder()
                .requestId(requestId)
                .publishService("core")
                .request(LogRequestDto.builder()
                        .url(path)
                        .method(httpMethod)
                        .headers(requestHeaders)
                        .body(new String(requestBody, StandardCharsets.UTF_8))
                        .build())
                .response(LogResponseDto.builder()
                        .status(httpStatus)
                        .headers(responseHeaders)
                        .body(new String(responseBody, StandardCharsets.UTF_8))
                        .build())
                .otherInfo("{\"nanosElapsed\":\"" + nanosElapsed + "\"}")
                .build());
    }

    private Map<String, String> toHeaderMap(
            Iterator<String> headerNameIterator,
            UnaryOperator<String> headerValueFunction
    ) {
        Map<String, String> headerMap = new HashMap<>();
        while (headerNameIterator.hasNext()) {
            String headerName = headerNameIterator.next();
            headerMap.put(headerName, headerValueFunction.apply(headerName));
        }
        return headerMap;
    }
}
