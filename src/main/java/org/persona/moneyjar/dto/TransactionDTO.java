package org.persona.moneyjar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.persona.moneyjar.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 14:01
 **/
@Data
public class TransactionDTO {
    @NotNull(message = "Card Id is mandatory")
    private UUID cardId;
    @NotNull(message = "Type is mandatory")
    private TransactionType type;
    @NotNull(message = "Amount is mandatory")
    private BigDecimal amount;
    @NotBlank(message = "Note is mandatory")
    private String note;
    private String description;
    private LocalDateTime createdAt;
}
