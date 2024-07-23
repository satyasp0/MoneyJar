package org.persona.moneyjar.dto;

import lombok.Data;
import org.persona.moneyjar.enums.CardType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Satya
 * @created 09/07/2024 - 09:57
 **/
@Data
public class CardDTO {
    private UUID id;
    private String name;
    private UUID userId;
    private CardType type;
    private BigDecimal amount = BigDecimal.ZERO;
}
