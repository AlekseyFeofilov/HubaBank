package org.huba.logger.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.Map;

@Data
public class Request {
    private String url;
    private String method;
    @ElementCollection
    @CollectionTable(name="request_headers")
    private Map<String, String> headers;
    @Column(name = "request_body")
    private String body;
}
