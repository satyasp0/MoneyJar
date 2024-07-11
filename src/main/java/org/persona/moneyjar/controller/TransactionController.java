package org.persona.moneyjar.controller;

import org.persona.moneyjar.dto.BaseResponseDto;
import org.persona.moneyjar.dto.TransactionDTO;
import org.persona.moneyjar.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Satya
 * @created 11/07/2024 - 09:41
 **/
@RestController
@RequestMapping("transaction")
public class TransactionController extends BaseController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> createTransaction(@Validated @RequestBody TransactionDTO dto){
        String id = transactionService.createTransaction(dto);
        if(id!=null){
            return send201(id);
        }else {
            return send400("card does not exist");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getTransactionById(@Validated @PathVariable UUID id){
        Optional<TransactionDTO> transactionDTO = transactionService.findTransactionById(id);

        if (transactionDTO.isPresent()){
            return send200(transactionDTO);
        }else {
            return send400("transaction not found");
        }
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<BaseResponseDto> getTransactionByCardId(@Validated @PathVariable UUID id){
        Optional<List<TransactionDTO>> transactionDTOList = transactionService.findTransactionByCardId(id);
        if (transactionDTOList.isPresent()){
            return send200(transactionDTOList);
        }else return send400("card not found");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<BaseResponseDto> getTransactionByUserId(@Validated @PathVariable UUID id){
        Optional<List<TransactionDTO>> transactionDTOList = transactionService.findTransactionByUserId(id);
        if (transactionDTOList.isPresent()){
            return send200(transactionDTOList);
        }else return send400("user not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateTransaction(@Validated @RequestBody TransactionDTO dto,
                                                             @PathVariable UUID id){
        if(transactionService.updateTransaction(id, dto)){
            return send200("transaction updated");
        }else return send400("transaction does not exist");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteTransaction(@Validated @PathVariable UUID id){
        if(transactionService.deleteTransaction(id)){
            return send200("transaction deleted");
        }else return send400("does not exist");
    }
}
