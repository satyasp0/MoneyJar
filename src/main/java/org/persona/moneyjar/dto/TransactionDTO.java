package org.persona.moneyjar.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.persona.moneyjar.enums.TransactionType;

import java.math.BigDecimal;

/**
 * @author Satya
 * @created 05/07/2024 - 14:01
 **/
@Data
public class TransactionDTO {
    @NotBlank(message = "Type is mandatory")
    private TransactionType type;
    @NotBlank(message = "Amount is mandatory")
    private BigDecimal amount;
    @NotBlank(message = "Note is mandatory")
    private String note;
    private String description;
}
