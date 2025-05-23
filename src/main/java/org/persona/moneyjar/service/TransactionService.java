package org.persona.moneyjar.service;

import org.persona.moneyjar.model.dto.TransactionDTO;

import java.util.List;


/**
 * @author Satya
 * @created 10/07/2024 - 16:03
 **/
public interface TransactionService {
    String createTransaction(TransactionDTO transaction);
    TransactionDTO findTransactionById(Long id);
    void updateTransaction(Long id, TransactionDTO transaction);
    void deleteTransaction(Long id);
    List<TransactionDTO> findTransactionByCardId(Long id);
    List<TransactionDTO> findTransactionByUser();
}
