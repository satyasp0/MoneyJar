package org.persona.moneyjar.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * @author Satya
 * @created 04/07/2024 - 14:16
 **/

@Data
@Entity
@Table(name = "users")
public class User extends BaseEnitity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String password;
    private String name;
    private String picture;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Card> cards;
    private boolean enabled = true;
}
