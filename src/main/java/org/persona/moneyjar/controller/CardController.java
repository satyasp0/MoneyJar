package org.persona.moneyjar.controller;

import org.persona.moneyjar.dto.BaseResponseDto;
import org.persona.moneyjar.dto.CardDTO;
import org.persona.moneyjar.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Satya
 * @created 09/07/2024 - 10:12
 **/
@RestController
@RequestMapping("card")
public class CardController extends BaseController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> createCard(@Validated @RequestBody CardDTO cardDTO) {
        String id = cardService.createCard(cardDTO);
        if (id!=null) {
            return send201(id);
        } else {
            return send404("Failed to create card");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getCardById(@PathVariable UUID id) {
        CardDTO cardList = cardService.getCardById(id);
        if (cardList!=null) {
            return send200(cardList);
        }else {
            return send404("card not found");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<BaseResponseDto> getCardsByUserId(@PathVariable UUID id) {
        List<CardDTO> cardDTOList = cardService.getAllCardsByUserId(id);
        if (!cardDTOList.isEmpty()) {
            return send200(cardDTOList);
        }else {
            return send404("No cards found for user id " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateCard(
            @PathVariable("id") UUID id,
            @Validated @RequestBody CardDTO cardDTO) {
        boolean updatedCard = cardService.updateCard(id, cardDTO);
        if (updatedCard) {
            return send200("Card Updated");
        } else {
            return send404("Card not found or update failed");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteCard(@PathVariable("id") UUID id) {
        boolean deleted = cardService.deleteCard(id);
        if (deleted) {
            return send200("Card deleted successfully");
        } else {
            return send404("Card not found");
        }
    }
}
