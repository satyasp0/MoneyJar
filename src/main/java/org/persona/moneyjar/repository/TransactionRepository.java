package org.persona.moneyjar.repository;

import org.persona.moneyjar.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 11:35
 **/
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findTransactionsByCardIdOrderByCreatedAtDesc(UUID id);
    List<Transaction> findTransactionsByCardUserIdOrderByCreatedAtDesc(UUID id);
}
