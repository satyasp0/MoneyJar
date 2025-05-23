package org.persona.moneyjar.repository;

import org.persona.moneyjar.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author Satya
 * @created 05/07/2024 - 11:35
 **/
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByCardIdOrderByCreatedAtDesc(Long id);

    @Query("SELECT trx FROM Transaction trx " +
           "LEFT JOIN Card crd on crd.id = trx.cardId " +
           "LEFT JOIN User usr ON usr.id = crd.userId " +
           "WHERE usr.id = :id")
    List<Transaction> findTransactionsByUserId(@Param("id") Long id);
}
