package org.persona.moneyjar.service.impl;

import org.persona.moneyjar.dto.TransactionDTO;
import org.persona.moneyjar.entity.Card;
import org.persona.moneyjar.entity.Transaction;
import org.persona.moneyjar.mapper.TransactionMapper;
import org.persona.moneyjar.repository.CardRepository;
import org.persona.moneyjar.repository.TransactionRepository;
import org.persona.moneyjar.repository.UserRepository;
import org.persona.moneyjar.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.persona.moneyjar.utils.MapperUtils.*;
import static org.persona.moneyjar.utils.TransactionUtils.calculateTransaction;

/**
 * @author Satya
 * @created 10/07/2024 - 16:06
 **/

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CardRepository cardRepository,
                                  TransactionMapper transactionMapper,
                                  UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
    }

    @Override
    public String createTransaction(TransactionDTO dto) {
        Optional<Card> cardOptional = cardRepository.findById(dto.getCardId());
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            BigDecimal afterTrasaction = calculateTransaction(card.getAmount(), dto.getAmount(), dto.getType());
            Transaction transactionToSave = transactionMapper.dtoToEntity(dto);
            transactionToSave.setCard(cardOptional.get());
            transactionToSave.setInitialBalance(card.getAmount());
            transactionToSave.setFinalBalance(afterTrasaction);
            Transaction successTransaction = transactionRepository.saveAndFlush(transactionToSave);
            card.setAmount(afterTrasaction);
            cardRepository.save(card);
            return successTransaction.getId().toString();
        }
        return null;
    }

    @Override
    public Optional<TransactionDTO> findTransactionById(UUID id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.map(transactionMapper::entityToDto);
    }

    @Override
    public boolean updateTransaction(UUID id, TransactionDTO dto) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transactionToUpdate = transactionOptional.get();
            updateField(dto.getDescription(),transactionToUpdate::setDescription);
            updateField(dto.getNote(),transactionToUpdate::setNote);
            updateTransactionType(dto.getType(),transactionToUpdate::setType);
            updateBigDecimal(dto.getAmount(),transactionToUpdate::setAmount);
            Transaction updated = transactionRepository.save(transactionToUpdate);
            updateCard(updated);
        }
        return false;
    }

    @Override
    public boolean deleteTransaction(UUID id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            transactionRepository.delete(transaction.get());
            updateCard(transaction.get());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Optional<List<TransactionDTO>> findTransactionByCardId(UUID id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()){
            List<Transaction> optionalTransactions = transactionRepository.findTransactionsByCardIdOrderByCreatedAtDesc(id);
            return Optional.of(optionalTransactions.stream().map(transactionMapper::entityToDto).toList());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<TransactionDTO>> findTransactionByUserId(UUID id) {
        if(userRepository.findById(id).isPresent()){
            List<Transaction> transactionList = transactionRepository.findTransactionsByCardUserIdOrderByCreatedAtDesc(id);
            return Optional.of(transactionList.stream().map(transactionMapper::entityToDto).toList());
        }
        return Optional.empty();
    }

    private void updateCard(Transaction transaction) {
        Optional<Card> card = cardRepository.findById(transaction.getCard().getId());
        if(card.isPresent()){
            BigDecimal afterTrasaction = calculateTransaction(card.get().getAmount(), transaction.getAmount(), transaction.getType());
            Card existCard = card.get();
            existCard.setAmount(afterTrasaction);
            cardRepository.save(existCard);
        }
    }
}
