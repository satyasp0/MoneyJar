package org.persona.moneyjar.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.persona.moneyjar.enums.CardType;

import java.math.BigDecimal;

/**
 * @author Satya
 * @created 05/07/2024 - 09:14
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "cards")
public class Card extends BaseEntity {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "card_type")
    private CardType cardType;
    private BigDecimal amount;

}
