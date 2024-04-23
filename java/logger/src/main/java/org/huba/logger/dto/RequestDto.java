package org.huba.logger.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RequestDto {
    private String url;
    private String method;
    private Map<String, String> headers;
    private String body;
}
