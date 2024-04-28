package org.huba.users.repository;

import org.huba.users.model.IdempotentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IdempotentRepository extends CrudRepository<IdempotentEntity, UUID> {
}
