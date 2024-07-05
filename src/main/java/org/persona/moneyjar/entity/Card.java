package org.persona.moneyjar.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.persona.moneyjar.enums.CardType;

import java.util.Set;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 09:14
 **/

@Data
@Entity
@Table(name = "cards")
public class Card extends BaseEnitity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "card_type")
    private CardType cardType;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

}
