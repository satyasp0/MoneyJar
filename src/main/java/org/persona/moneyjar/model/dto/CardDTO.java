package org.persona.moneyjar.model.dto;

import lombok.Data;
import org.persona.moneyjar.enums.CardType;

import java.math.BigDecimal;


/**
 * @author Satya
 * @created 09/07/2024 - 09:57
 **/
@Data
public class CardDTO {
    private Long id;
    private String name;
    private CardType type;
    private BigDecimal amount = BigDecimal.ZERO;
}
