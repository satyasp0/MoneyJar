package org.persona.moneyjar.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.persona.moneyjar.enums.TransactionType;

import java.math.BigDecimal;


/**
 * @author Satya
 * @created 05/07/2024 - 09:23
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_id")
    private Long cardId;
    private TransactionType type;
    private BigDecimal amount;
    @Column(name = "initial_balance")
    private BigDecimal initialBalance;
    @Column(name = "final_balance")
    private BigDecimal finalBalance;
    private String note;
    private String description;
}
