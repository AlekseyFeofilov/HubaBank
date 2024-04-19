package org.huba.logger.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.huba.logger.dto.PublishLogDto;
import org.huba.logger.model.LogEntity;
import org.huba.logger.model.Request;
import org.huba.logger.model.Response;
import org.huba.logger.model.StackTraceEntity;
import org.huba.logger.repository.LogRepository;
import org.huba.logger.repository.StackTraceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final StackTraceRepository stackTraceRepository;
    @Transactional
    public void publishLog(PublishLogDto dto) {
        LogEntity logEntity = logRepository.findById(dto.getRequestId()).orElse(null);
        if(logEntity == null) {
            logEntity = new LogEntity();
            logEntity.setRequestId(dto.getRequestId());
            logEntity = logRepository.save(logEntity);
        }
        StackTraceEntity stackTraceEntity = new StackTraceEntity();
        stackTraceEntity.setDate(LocalDateTime.now());
        stackTraceEntity.setOtherInfo(dto.getOtherInfo());
        stackTraceEntity.setLogEntity(logEntity);
        stackTraceEntity.setPublishService(dto.getPublishService());

        Request request = new Request();
        request.setBody(dto.getRequest().getBody());
        request.setUrl(dto.getRequest().getUrl());
        request.setMethod(dto.getRequest().getMethod());
        request.setHeaders(dto.getRequest().getHeaders());

        Response response = new Response();
        response.setBody(dto.getResponse().getBody());
        response.setHeaders(dto.getResponse().getHeaders());
        response.setStatus(dto.getResponse().getStatus());

        stackTraceEntity.setRequest(request);
        stackTraceEntity.setResponse(response);

        stackTraceRepository.save(stackTraceEntity);
        stackTraceEntity.getLogEntity().getStackTrace().add(stackTraceEntity);
        logRepository.save(stackTraceEntity.getLogEntity());
    }
}
