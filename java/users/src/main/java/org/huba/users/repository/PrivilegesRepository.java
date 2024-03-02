package org.huba.users.repository;

import org.huba.users.model.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegesRepository extends CrudRepository<Privilege, String> {
    List<Privilege> getAll();
}
