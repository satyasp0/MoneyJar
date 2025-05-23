package org.persona.moneyjar.mapper;

import org.persona.moneyjar.model.dto.TransactionDTO;
import org.persona.moneyjar.model.entity.Transaction;
import org.springframework.stereotype.Component;

/**
 * @author Satya
 * @created 10/07/2024 - 16:12
 **/

@Component
public class TransactionMapper {

    public Transaction dtoToEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setType(dto.getType());
        transaction.setNote(dto.getNote());
        transaction.setAmount(dto.getAmount());
        transaction.setCardId(dto.getCardId());
        return transaction;
    }

    public TransactionDTO entityToDto(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDescription(transaction.getDescription());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setNote(transaction.getNote());
        transactionDTO.setCardId(transaction.getCardId());
        transactionDTO.setCreatedAt(transaction.getCreatedAt());
        return transactionDTO;
    }
}
