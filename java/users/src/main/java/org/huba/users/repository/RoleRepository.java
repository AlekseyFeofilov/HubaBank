package org.huba.users.repository;

import org.huba.users.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
    List<Role> findAll();
}
