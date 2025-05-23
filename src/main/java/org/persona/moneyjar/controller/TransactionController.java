package org.persona.moneyjar.controller;

import lombok.RequiredArgsConstructor;
import org.persona.moneyjar.model.dto.BaseResponseDto;
import org.persona.moneyjar.model.dto.TransactionDTO;
import org.persona.moneyjar.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Satya
 * @created 11/07/2024 - 09:41
 **/
@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController extends BaseController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createTransaction(@Validated @RequestBody TransactionDTO dto){
        String id = transactionService.createTransaction(dto);
        return send201(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getTransactionById(@Validated @PathVariable Long id){
        TransactionDTO transactionDTO = transactionService.findTransactionById(id);
        return send200(transactionDTO);
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<BaseResponseDto> getTransactionByCardId(@Validated @PathVariable Long id){
        List<TransactionDTO> transactionDTOList = transactionService.findTransactionByCardId(id);
        return send200(transactionDTOList);
    }

    @GetMapping("/user")
    public ResponseEntity<BaseResponseDto> getTransactionByUserId(){
        List<TransactionDTO> transactionDTOList = transactionService.findTransactionByUser();
        return send200(transactionDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateTransaction(@Validated @RequestBody TransactionDTO dto, @PathVariable Long id){
        transactionService.updateTransaction(id, dto);
        return send200("transaction updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteTransaction(@Validated @PathVariable Long id){
        transactionService.deleteTransaction(id);
        return send200("transaction deleted");
    }
}
