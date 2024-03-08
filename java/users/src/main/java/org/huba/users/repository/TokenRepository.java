package org.huba.users.repository;

import org.huba.users.model.Token;
import org.huba.users.model.TokenType;
import org.huba.users.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends CrudRepository<Token, UUID> {
    Optional<Token> findByValueAndAndType(String value, TokenType type);
    List<Token> findAllByUserAndType(User user, TokenType type);
}
