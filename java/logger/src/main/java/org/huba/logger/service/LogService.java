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
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final StackTraceRepository stackTraceRepository;
    int limit = 4500;
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
        stackTraceEntity.setOtherInfo(toLim(dto.getOtherInfo()));
        stackTraceEntity.setLogEntity(logEntity);
        stackTraceEntity.setPublishService(toLim(dto.getPublishService()));

        Request request = new Request();
        request.setBody(toLim(dto.getRequest().getBody()));
        request.setUrl(toLim(dto.getRequest().getUrl()));
        request.setMethod(toLim(dto.getRequest().getMethod()));
        request.setHeaders(toLimit(dto.getRequest().getHeaders()));

        Response response = new Response();
        response.setBody(toLim(dto.getResponse().getBody()));
        response.setHeaders(toLimit(dto.getResponse().getHeaders()));
        response.setStatus(dto.getResponse().getStatus());

        stackTraceEntity.setRequest(request);
        stackTraceEntity.setResponse(response);

        stackTraceRepository.save(stackTraceEntity);
        stackTraceEntity.getLogEntity().getStackTrace().add(stackTraceEntity);
        logRepository.save(stackTraceEntity.getLogEntity());
    }

    public Double getPercent(LocalDateTime time, String serviceName) {
        if(time == null) {
            time = LocalDateTime.now().minusMinutes(1);
        }
        List<StackTraceEntity> list = stackTraceRepository.findByDateAfterAndPublishService(time, serviceName);
        double normal = 0d;
        double errors = 0d;

        for(StackTraceEntity stackTraceEntity : list) {
            if(stackTraceEntity.getResponse().getStatus() >= 500) {
                errors += 1d;
            }
            else {
                normal += 1d;
            }
        }
        if(errors == 0) {
            return 0d;
        }
        return errors / (normal + errors);
    }

    private Map<String, String> toLimit(Map<String, String> headers) {
        Map<String, String> headersNew = new HashMap<>();
        for(String header:headers.keySet()) {
            headersNew.put(toLim(header), toLim(headers.get(header)));
        }
        return headersNew;
    }
    private String toLim(String str) {
        if(str == null) {
            return str;
        }
        if(str.length() >= 5000) {
            return str.substring(0, limit);
        }
        return str;
    }
}
