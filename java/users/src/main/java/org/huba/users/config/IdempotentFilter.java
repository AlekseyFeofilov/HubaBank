package org.huba.users.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.huba.users.model.IdempotentEntity;
import org.huba.users.repository.IdempotentRepository;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE + 2)
@Component
@WebFilter(filterName = "IdempotentFilter", urlPatterns = "/*")
@RequiredArgsConstructor
public class IdempotentFilter extends OncePerRequestFilter {
    private final IdempotentRepository idempotentRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UUID key = null;
        if(!request.getMethod().equals("GET")) {
            String str = request.getHeader("idempotentKey");
            if(str != null) {
                key = UUID.fromString(str);
            } else {
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        if (key != null) {
            IdempotentEntity entity = idempotentRepository.findById(key).orElse(null);
            if(entity == null) {
                ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
                ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

                filterChain.doFilter(requestWrapper, responseWrapper);

                entity = new IdempotentEntity();
                entity.setRequestId(key);
                entity.setStatus(responseWrapper.getStatus());
                entity.setBody(getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding()));
                idempotentRepository.save(entity);
                responseWrapper.copyBodyToResponse();
            }
            else {
                response.setStatus(entity.getStatus());
                response.setContentType("application/json");
                response.getWriter().write(entity.getBody());
                response.getWriter().flush();
                response.getWriter().close();
            }
        }
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
