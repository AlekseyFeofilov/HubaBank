package org.huba.logger.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PublishLogDto {
    private UUID requestId;
    private String publishService;
    private RequestDto request;
    private ResponseDto response;
    private String otherInfo;
}
