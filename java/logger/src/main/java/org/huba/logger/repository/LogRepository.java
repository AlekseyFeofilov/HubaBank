package org.huba.logger.repository;

import org.huba.logger.model.LogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepository extends CrudRepository<LogEntity, UUID> {

}
