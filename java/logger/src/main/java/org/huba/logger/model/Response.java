package org.huba.logger.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private Integer status;
    @ElementCollection
    @CollectionTable(name="response_headers")
    @Size(max = 5000)
    private Map<String, String> headers;
    @Column(name = "response_body")
    @Size(max = 5000)
    private String body;
}
