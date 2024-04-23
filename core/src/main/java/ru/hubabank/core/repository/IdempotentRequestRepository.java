package ru.hubabank.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hubabank.core.entity.IdempotentRequest;
import ru.hubabank.core.entity.IdempotentRequestId;

public interface IdempotentRequestRepository extends JpaRepository<IdempotentRequest, IdempotentRequestId> {
}
