package org.huba.users.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.huba.users.dto.log.PublishLogDto;
import org.huba.users.dto.log.RequestDto;
import org.huba.users.dto.log.ResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Order(Ordered.LOWEST_PRECEDENCE - 2)
@Component
@WebFilter(filterName = "LogFilter", urlPatterns = "/*")
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        UUID requestId;
        String re = request.getHeader("requestId");
        if(re != null) {
            try {
                requestId = UUID.fromString(re);
            } catch (RuntimeException e) {
                requestId = UUID.randomUUID();
            }
        } else {
            requestId = UUID.randomUUID();
        }

        RequestDto requestDto = new RequestDto();
        requestDto.setUrl(request.getRequestURI());
        requestDto.setMethod(request.getMethod());
        Map<String, String> requestHeaders = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(header -> {
            requestHeaders.put(header, request.getHeader(header));
        });
        requestDto.setHeaders(requestHeaders);
        try {
            requestDto.setBody(getStringValue(requestWrapper.getContentAsByteArray(),
                    request.getCharacterEncoding()));
        } catch (RuntimeException e) {

        }

        ResponseDto responseDto = new ResponseDto();

        Map<String, String> responseHeaders = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(header -> {
            responseHeaders.put(header, response.getHeader(header));
        });
        responseDto.setHeaders(requestHeaders);
        responseDto.setStatus(response.getStatus());
        try {
            responseDto.setBody(getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding()));
        } catch (RuntimeException e) {

        }

        PublishLogDto publishLogDto = new PublishLogDto();
        publishLogDto.setResponse(responseDto);
        publishLogDto.setRequest(requestDto);
        publishLogDto.setPublishService("user");
        publishLogDto.setOtherInfo("{}");
        publishLogDto.setRequestId(requestId);

        try{
            RestClient restClient = RestClient.create();
            restClient.post().uri("http://194.147.90.192:9006/log/api/v1")
                    .contentType(APPLICATION_JSON)
                    .body(publishLogDto)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RuntimeException e) {

        }

        responseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
