package org.huba.users.dto.log;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseDto {
    private Integer status;
    private Map<String, String> headers;
    private String body;
}
