package org.persona.moneyjar.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.persona.moneyjar.enums.CardType;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 09:14
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cards")
public class Card extends BaseEnitity {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "card_type")
    private CardType cardType;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
    private BigDecimal amount;

}
