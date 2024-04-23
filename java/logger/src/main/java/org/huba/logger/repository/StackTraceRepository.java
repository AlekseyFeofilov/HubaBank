package org.huba.logger.repository;

import org.huba.logger.model.LogEntity;
import org.huba.logger.model.StackTraceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StackTraceRepository extends CrudRepository<StackTraceEntity, LogEntity> {

}
