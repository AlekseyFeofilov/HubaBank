package org.huba.logger.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

@Data
public class Request {
    private String url;
    private String method;
    @ElementCollection
    @CollectionTable(name="request_headers")
    @Size(max = 5000)
    private Map<String, String> headers;
    @Size(max = 5000)
    @Column(name = "request_body")
    private String body;
}
