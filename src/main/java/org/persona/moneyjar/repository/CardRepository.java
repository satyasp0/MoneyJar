package org.persona.moneyjar.repository;

import org.persona.moneyjar.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * @author Satya
 * @created 05/07/2024 - 11:34
 **/
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findCardsByUserId(Long userId);
    Optional<Card> findCardByIdAndUserId(Long id, Long userId);
}
