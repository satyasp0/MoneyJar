package org.persona.moneyjar.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.persona.moneyjar.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 09:23
 **/

@Data
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEnitity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    private TransactionType type;
    private BigDecimal amount;
    @Column(name = "initial_balance")
    private BigDecimal initialBalance;
    @Column(name = "final_balance")
    private BigDecimal finalBalance;
    private String note;
    private String description;
}
