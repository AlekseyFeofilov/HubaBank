package org.huba.logger.controller;

import lombok.RequiredArgsConstructor;
import org.huba.logger.dto.PublishLogDto;
import org.huba.logger.exception.NotImplementedException;
import org.huba.logger.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.huba.logger.utils.MyConstants.LOG_API;

@RestController
@RequestMapping(LOG_API)
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;
    @PostMapping
    public void publishLog(@RequestBody PublishLogDto dto) {
        logService.publishLog(dto);
    }

    @GetMapping("errors/percent")
    public Double getPercent(@RequestParam(value = "time", required = false) LocalDateTime time, String serviceName) {
        return logService.getPercent(time, serviceName);
    }

    @GetMapping("errors/percent/second")
    public Double getPercent(Integer second, String serviceName) {
        return logService.getPercent(LocalDateTime.now().minusSeconds(second), serviceName);
    }
}
