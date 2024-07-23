package org.persona.moneyjar.service;

import org.persona.moneyjar.dto.CardDTO;

import java.util.List;
import java.util.UUID;

/**
 * @author Satya
 * @created 09/07/2024 - 09:56
 **/
public interface CardService {

    String createCard(CardDTO cardDTO);

    CardDTO getCardById(UUID id);

    boolean updateCard(UUID cardId, CardDTO cardDTO);

    boolean deleteCard(UUID cardId);

    List<CardDTO> getAllCardsByUserId(UUID userId);
}
