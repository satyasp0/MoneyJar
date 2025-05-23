package org.persona.moneyjar.service;

import org.persona.moneyjar.model.dto.CardDTO;

import java.util.List;

/**
 * @author Satya
 * @created 09/07/2024 - 09:56
 **/
public interface CardService {

    Long createCard(CardDTO cardDTO);

    CardDTO getCardById(Long id);

    void updateCard(Long cardId, CardDTO cardDTO);

    void deleteCard(Long cardId);

    List<CardDTO> getAllCardsByUser();
}
