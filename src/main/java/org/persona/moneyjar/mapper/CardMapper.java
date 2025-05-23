package org.persona.moneyjar.mapper;

import org.persona.moneyjar.model.dto.CardDTO;
import org.persona.moneyjar.model.entity.Card;
import org.springframework.stereotype.Component;

/**
 * @author Satya
 * @created 09/07/2024 - 10:00
 **/
@Component
public class CardMapper {
    public Card dtoToEnitity(CardDTO cardDTO, Long userId) {
        Card card = new Card();
        card.setName(cardDTO.getName());
        card.setUserId(userId);
        card.setCardType(cardDTO.getType());
        card.setAmount(cardDTO.getAmount());
        return card;
    }

    public CardDTO enitityToDto(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setName(card.getName());
        cardDTO.setType(card.getCardType());
        cardDTO.setAmount(card.getAmount());
        return cardDTO;
    }
}
