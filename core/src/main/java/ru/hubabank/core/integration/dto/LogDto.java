package ru.hubabank.core.integration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogDto {

    private String requestId;
    private String publishService;
    private LogRequestDto request;
    private LogResponseDto response;
    private String otherInfo;
}