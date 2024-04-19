package org.huba.logger.controller;

import lombok.RequiredArgsConstructor;
import org.huba.logger.dto.PublishLogDto;
import org.huba.logger.exception.NotImplementedException;
import org.huba.logger.service.LogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
