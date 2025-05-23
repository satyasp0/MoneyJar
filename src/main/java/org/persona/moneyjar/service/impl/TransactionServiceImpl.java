package org.persona.moneyjar.service.impl;

import lombok.RequiredArgsConstructor;
import org.persona.moneyjar.enums.TransactionType;
import org.persona.moneyjar.exception.MoneyJarException;
import org.persona.moneyjar.model.dto.TransactionDTO;
import org.persona.moneyjar.model.entity.Card;
import org.persona.moneyjar.model.entity.Transaction;
import org.persona.moneyjar.mapper.TransactionMapper;
import org.persona.moneyjar.repository.CardRepository;
import org.persona.moneyjar.repository.TransactionRepository;
import org.persona.moneyjar.service.TransactionService;
import org.persona.moneyjar.utils.JwtInformationUtil;
import org.persona.moneyjar.utils.TransactionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import static org.persona.moneyjar.utils.MapperUtils.*;

/**
 * @author Satya
 * @created 10/07/2024 - 16:06
 **/

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public String createTransaction(TransactionDTO dto) {
        Optional<Card> cardOptional = cardRepository.findById(dto.getCardId());
        if (cardOptional.isEmpty()) throw MoneyJarException.cardNotFoundError();
        Card card = cardOptional.get();
        BigDecimal afterTransaction = TransactionUtils.calculateTransaction(card.getAmount(), dto.getAmount(), dto.getType());
        Transaction transactionToSave = transactionMapper.dtoToEntity(dto);
        transactionToSave.setInitialBalance(card.getAmount());
        transactionToSave.setFinalBalance(afterTransaction);
        Transaction successTransaction = transactionRepository.saveAndFlush(transactionToSave);
        card.setAmount(afterTransaction);
        cardRepository.save(card);
        return successTransaction.getId().toString();
    }

    @Override
    public TransactionDTO findTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) throw MoneyJarException.transactionNotFoundError();
        return transactionMapper.entityToDto(transaction.get());
    }

    @Override
    public void updateTransaction(Long id, TransactionDTO dto) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) throw MoneyJarException.transactionNotFoundError();
        BigDecimal prevAmount = transactionOptional.get().getAmount();
        Transaction transactionToUpdate = transactionOptional.get();
        BigDecimal afterTransaction = TransactionUtils.calculateUpdateTransaction(transactionToUpdate.getFinalBalance(),transactionToUpdate.getAmount(), dto.getAmount(), dto.getType());
        updateField(dto.getDescription(), transactionToUpdate::setDescription);
        updateField(dto.getNote(), transactionToUpdate::setNote);
        updateTransactionType(dto.getType(), transactionToUpdate::setType);
        updateBigDecimal(dto.getAmount(), transactionToUpdate::setAmount);
        updateBigDecimal(afterTransaction, transactionToUpdate::setFinalBalance);
        transactionRepository.save(transactionToUpdate);
        updateCard(transactionOptional.get().getCardId(), prevAmount, dto.getAmount(), transactionOptional.get().getType());
    }

    @Override
    public void deleteTransaction(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) throw MoneyJarException.transactionNotFoundError();
        transactionRepository.delete(transaction.get());
        updateCard(transaction.get().getCardId(), transaction.get().getAmount(), BigDecimal.valueOf(0), transaction.get().getType());
    }

    @Override
    public List<TransactionDTO> findTransactionByCardId(Long id) {
        List<Transaction> transactions = transactionRepository.findTransactionsByCardIdOrderByCreatedAtDesc(id);
        return transactions.stream()
                .map(transactionMapper::entityToDto)
                .toList();
    }

    @Override
    public List<TransactionDTO> findTransactionByUser() {
        List<Transaction> transactionList = transactionRepository.findTransactionsByUserId(JwtInformationUtil.getUserDetails().getId());
        return transactionList.stream().map(transactionMapper::entityToDto).toList();
    }

    private void updateCard(Long cardId, BigDecimal before, BigDecimal after, TransactionType type) {
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isEmpty()) throw MoneyJarException.cardNotFoundError();
        BigDecimal afterTransaction = TransactionUtils.calculateUpdateTransaction(card.get().getAmount(), before, after, type);
        Card existCard = card.get();
        existCard.setAmount(afterTransaction);
        cardRepository.save(existCard);
    }
}
