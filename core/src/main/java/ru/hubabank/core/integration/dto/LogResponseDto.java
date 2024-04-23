package ru.hubabank.core.integration.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class LogResponseDto {

    private int status;
    private Map<String, String> headers;
    private String body;
}