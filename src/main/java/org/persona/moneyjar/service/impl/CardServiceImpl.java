package org.persona.moneyjar.service.impl;

import org.persona.moneyjar.dto.CardDTO;
import org.persona.moneyjar.entity.Card;
import org.persona.moneyjar.entity.User;
import org.persona.moneyjar.mapper.CardMapper;
import org.persona.moneyjar.repository.CardRepository;
import org.persona.moneyjar.repository.UserRepository;
import org.persona.moneyjar.service.CardService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.persona.moneyjar.utils.MapperUtils.updateCardType;
import static org.persona.moneyjar.utils.MapperUtils.updateField;

/**
 * @author Satya
 * @created 09/07/2024 - 10:00
 **/
@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;

    public CardServiceImpl(CardRepository cardRepository, CardMapper cardMapper, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.userRepository = userRepository;
    }

    @Override
    public String createCard(CardDTO cardDTO) {
        Optional<User> newUser = userRepository.findById(cardDTO.getUserId());
        if (newUser.isPresent()) {
            Card card = cardMapper.dtoToEnitity(cardDTO, newUser.get());
            Card savedCard = cardRepository.save(card);
            return Optional.of(savedCard.getId().toString()).orElse(null);
        } else {
            return null;
        }

    }

    @Override
    public CardDTO getCardById(UUID id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return optionalCard.map(cardMapper::enitityToDto).orElse(null);
    }

    @Override
    public boolean updateCard(UUID cardId, CardDTO cardDTO) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card existingCard = optionalCard.get();
            updateField(cardDTO.getName(), existingCard::setName);
            updateCardType(cardDTO.getType(), existingCard::setCardType);

            cardRepository.save(existingCard);
            return true;
        } else return false;
    }

    @Override
    public boolean deleteCard(UUID cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            cardRepository.delete(optionalCard.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CardDTO> getAllCardsByUserId(UUID userId) {
        if (userRepository.findById(userId).isPresent()) {
            List<Card> cardList = cardRepository.findCardsByUserId(userId);
            return cardList.stream().map(cardMapper::enitityToDto).toList();
        }else {
            return Collections.emptyList();
        }
    }
}