package org.huba.logger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class LogEntity {
    @Id
    private UUID requestId;

    @OneToMany(mappedBy = "logEntity")
    private List<StackTraceEntity> stackTrace = new ArrayList<>();
}
