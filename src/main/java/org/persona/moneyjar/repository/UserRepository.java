package org.persona.moneyjar.repository;

import org.persona.moneyjar.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Satya
 * @created 04/07/2024 - 14:56
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
