package org.huba.users.repository;

import org.huba.users.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Boolean existsByPhone(String phone);

    Optional<User> findByPhone(String phone);

    List<User> findAll();

    List<User> findByEmployee(boolean employee);
}
