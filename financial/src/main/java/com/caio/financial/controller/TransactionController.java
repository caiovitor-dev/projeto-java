package com.caio.financial.controller;

import com.caio.financial.dto.TransactionCreateDTO;
import com.caio.financial.dto.TransactionResponseDTO;
import com.caio.financial.dto.UpdateTransactionDTO;
import com.caio.financial.entity.Transaction;
import com.caio.financial.entity.TypeCategory;
import com.caio.financial.mapper.TransactionMapper;
import com.caio.financial.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionCreateDTO dto){
        Transaction transaction = transactionMapper.toEntity(dto);
        transactionService.saveTransaction(transaction,dto.categoryId(),dto.accountId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transaction.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransaction(@PathVariable String id){

        return transactionService.findTransactionById(UUID.fromString(id)).map(transaction -> {
            TransactionResponseDTO dto = transactionMapper.toDTO(transaction);

            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String id){

        return transactionService.findTransactionById(UUID.fromString(id)).map(transaction -> {

            transactionService.removeTransaction(transaction);
            return  ResponseEntity.noContent().build();

        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction (@PathVariable String id, @RequestBody UpdateTransactionDTO dto){

        return transactionService.findTransactionById(UUID.fromString(id)).map(transaction -> {
            Transaction entity = transactionMapper.toEntity(dto);

            BigDecimal transactionValueOld= transaction.getValue();
            TypeCategory typeCategory= transaction.getCategory().getTypeCategory();

            transaction.setValue(entity.getValue());
            transaction.setDescription(entity.getDescription());

            transactionService.updateCompleteTransaction(transaction,transactionValueOld,dto.categoryId(),typeCategory);

            return ResponseEntity.noContent().build();

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
