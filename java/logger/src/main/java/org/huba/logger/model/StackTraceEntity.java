package org.huba.logger.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class StackTraceEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "request_id", updatable = false, nullable = false)
    private LogEntity logEntity;

    private String publishService;

    private LocalDateTime date;

    @Embedded
    private Request request;

    @Embedded
    private Response response;

    private String otherInfo;

    @PrePersist
    public void init() {
        id = UUID.randomUUID();
    }
}

