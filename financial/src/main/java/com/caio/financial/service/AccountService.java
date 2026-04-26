package com.caio.financial.service;

import com.caio.financial.dto.UpdateAccountDTO;
import com.caio.financial.entity.*;
import com.caio.financial.mapper.AccountMapper;
import com.caio.financial.repository.AccountRepository;
import com.caio.financial.repository.UserRepository;
import com.caio.financial.security.SecurityService;
import com.caio.financial.validator.AccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountValidator accountValidator;
    private final AccountMapper accountMapper;
    private final SecurityService securityService;

    public Account saveAccount(Account account){


        account.setUser(securityService.getLoggedUser());

        accountValidator.validateAccount(account);
        return accountRepository.save(account);
    }

    public Optional<Account>getAccount(UUID accountId){
        return  accountRepository.findById(accountId);
    }

    public void updatePartial(Account account , UpdateAccountDTO dto){
        accountMapper.updateAccount(dto,account);
        accountRepository.save(account);
    }

    public void deleteAccount(Account account){
        accountRepository.delete(account);
    }

    public void validateAccountOwnership(Account account, User user){

        if(!account.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Conta não pertence ao usuário");
        }
    }





}
