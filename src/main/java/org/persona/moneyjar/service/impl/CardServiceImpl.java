package org.persona.moneyjar.service.impl;

import lombok.RequiredArgsConstructor;
import org.persona.moneyjar.exception.MoneyJarException;
import org.persona.moneyjar.model.dto.CardDTO;
import org.persona.moneyjar.model.entity.Card;
import org.persona.moneyjar.mapper.CardMapper;
import org.persona.moneyjar.repository.CardRepository;
import org.persona.moneyjar.service.CardService;
import org.persona.moneyjar.utils.JwtInformationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


import static org.persona.moneyjar.utils.MapperUtils.updateCardType;
import static org.persona.moneyjar.utils.MapperUtils.updateField;

/**
 * @author Satya
 * @created 09/07/2024 - 10:00
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    public Long createCard(CardDTO cardDTO) {
            Card card = cardMapper.dtoToEnitity(cardDTO, JwtInformationUtil.getUserDetails().getId());
            return cardRepository.save(card).getId();
    }

    @Override
    public CardDTO getCardById(Long id) {
        Optional<Card> optionalCard = cardRepository.findCardByIdAndUserId(id, JwtInformationUtil.getUserDetails().getId());
        if(optionalCard.isEmpty()) throw MoneyJarException.cardNotFoundError();
        return cardMapper.enitityToDto(optionalCard.get());
    }

    @Override
    public void updateCard(Long cardId, CardDTO cardDTO) {
        Optional<Card> optionalCard = cardRepository.findCardByIdAndUserId(cardId, JwtInformationUtil.getUserDetails().getId());
        if(optionalCard.isEmpty()) throw MoneyJarException.cardNotFoundError();
        Card existingCard = optionalCard.get();
        updateField(cardDTO.getName(), existingCard::setName);
        updateCardType(cardDTO.getType(), existingCard::setCardType);
        cardRepository.save(existingCard);
    }

    @Override
    public void deleteCard(Long cardId) {
        Optional<Card> optionalCard = cardRepository.findCardByIdAndUserId(cardId, JwtInformationUtil.getUserDetails().getId());
        if(optionalCard.isEmpty()) throw MoneyJarException.cardNotFoundError();
        cardRepository.delete(optionalCard.get());
    }

    @Override
    public List<CardDTO> getAllCardsByUser() {
            List<Card> cardList = cardRepository.findCardsByUserId(JwtInformationUtil.getUserDetails().getId());
            return cardList.stream().map(cardMapper::enitityToDto).toList();
    }
}