package org.huba.logger.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PublishLogDto {
    private UUID requestId;
    private List<String> stacktrace;
    private String publishService;
    private Integer status;
    private LocalDateTime date;
}
