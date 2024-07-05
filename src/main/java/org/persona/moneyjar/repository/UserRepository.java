package org.persona.moneyjar.repository;

import org.persona.moneyjar.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

/**
 * @author Satya
 * @created 04/07/2024 - 14:56
 **/
public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = "cards")
    Optional<User> findByUsername(String username);
}
