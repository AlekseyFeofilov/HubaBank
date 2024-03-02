package org.huba.users.repository;

import org.huba.users.model.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegesRepository extends CrudRepository<Privilege, String> {
}
