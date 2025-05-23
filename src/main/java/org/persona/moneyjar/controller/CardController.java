package org.persona.moneyjar.controller;

import lombok.RequiredArgsConstructor;
import org.persona.moneyjar.model.dto.BaseResponseDto;
import org.persona.moneyjar.model.dto.CardDTO;
import org.persona.moneyjar.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Satya
 * @created 09/07/2024 - 10:12
 **/
@RestController
@RequestMapping("card")
@RequiredArgsConstructor
public class CardController extends BaseController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createCard(@Validated @RequestBody CardDTO cardDto) {
        Long id = cardService.createCard(cardDto);
        return send201(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getCardById(@PathVariable Long id) {
        CardDTO card = cardService.getCardById(id);
        return send200(card);
    }

    @GetMapping("/user")
    public ResponseEntity<BaseResponseDto> getCardsByUser() {
        return send200(cardService.getAllCardsByUser());

    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateCard(@PathVariable("id") Long id, @Validated @RequestBody CardDTO cardDTO) {
        cardService.updateCard(id, cardDTO);
        return send200("Card Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteCard(@PathVariable("id") Long id) {
        cardService.deleteCard(id);
        return send200("Card deleted successfully");
    }
}
