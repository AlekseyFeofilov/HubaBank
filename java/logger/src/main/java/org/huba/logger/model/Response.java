package org.huba.logger.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private Integer status;
    @ElementCollection
    @CollectionTable(name="response_headers")
    private Map<String, String> headers;
    @Column(name = "response_body")
    private String body;
}
