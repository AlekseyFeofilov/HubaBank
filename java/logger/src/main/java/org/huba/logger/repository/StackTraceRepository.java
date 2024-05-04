package org.huba.logger.repository;

import org.huba.logger.model.LogEntity;
import org.huba.logger.model.StackTraceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StackTraceRepository extends CrudRepository<StackTraceEntity, LogEntity> {

    List<StackTraceEntity> findByDateAfterAndPublishService(LocalDateTime time, String publishService);

}
