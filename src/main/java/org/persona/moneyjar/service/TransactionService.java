package org.persona.moneyjar.service;

import org.persona.moneyjar.dto.TransactionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Satya
 * @created 10/07/2024 - 16:03
 **/
public interface TransactionService {
    String createTransaction(TransactionDTO transaction);
    Optional<TransactionDTO> findTransactionById(UUID id);
    boolean updateTransaction(UUID id, TransactionDTO transaction);
    boolean deleteTransaction(UUID id);
    Optional<List<TransactionDTO>> findTransactionByCardId(UUID id);
    Optional<List<TransactionDTO>> findTransactionByUserId(UUID id);
}
