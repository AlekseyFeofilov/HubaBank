package org.huba.users.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Dto {
    private UUID requestId;
    private List<String> tracePath;

    protected String getLogString() {
        return "requestId: " + requestId + " tracePath: " + tracePath;
    }
}
