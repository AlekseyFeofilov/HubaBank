package org.huba.users.dto.log;

import lombok.Data;

import java.util.UUID;

@Data
public class PublishLogDto {
    private UUID requestId;
    private String publishService;
    private RequestDto request;
    private ResponseDto response;
    private String otherInfo;
}
