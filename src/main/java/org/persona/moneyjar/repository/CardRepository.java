package org.persona.moneyjar.repository;

import org.persona.moneyjar.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 11:34
 **/
public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findCardsByUserId(UUID userId);
}
