package com.caio.financial.controller;

import com.caio.financial.dto.*;
import com.caio.financial.entity.Account;
import com.caio.financial.entity.Transaction;
import com.caio.financial.mapper.AccountMapper;
import com.caio.financial.mapper.TransactionMapper;
import com.caio.financial.service.AccountService;
import com.caio.financial.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("account")

@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class AccountController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody AccountCreateDTO dto) {
        Account account = accountMapper.toEntity(dto);
        accountService.saveAccount(account);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(account.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable String id) {

        return accountService.getAccount(UUID.fromString(id)).map(account -> {
            AccountResponseDTO dto = accountMapper.toDTO(account);
            return ResponseEntity.ok(dto);

        }).orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/allTransaction")
    public ResponseEntity<AccountTransactionResponseDTO> getAllTransaction(
            @RequestParam("id")UUID id,
            @RequestParam("numberPage") int numberPage,
            @RequestParam("numberSize") int numberSize
    ){

        return accountService.getAccount(id).map(account -> {

            Page<Transaction> page = transactionService.findAccountTransaction(id, numberPage, numberSize);

            List<TransactionResponseDTO> transaction =page.stream().map(transactionMapper::toDTO).toList();


            AccountTransactionResponseDTO response = accountMapper.response(account,transaction);


            return ResponseEntity.ok(response);

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateAccount(@PathVariable String id, @RequestBody UpdateAccountDTO dto) {

        return accountService.getAccount(UUID.fromString(id)).map(account -> {

            accountService.updatePartial(account,dto);
            return ResponseEntity.noContent().build();

        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id){

        return accountService.getAccount(UUID.fromString(id)).map(account -> {
            accountService.deleteAccount(account);
            return ResponseEntity.noContent().build();

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}