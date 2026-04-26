package com.caio.financial.validator;

import com.caio.financial.entity.Account;
import com.caio.financial.entity.User;
import com.caio.financial.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountValidator {


    private final AccountRepository accountRepository;

    public void validateAccount(Account account){
        if(existsAccount(account)){
            throw  new RuntimeException("Já existe uma conta "+account.getTypeAccount()+ "criada");
        }

    }

    public boolean existsAccount(Account account){

        Optional<Account> accountFound =
                accountRepository.findByUserIdAndTypeAccount(account.getUser().getId(), account.getTypeAccount());

        if(account.getId()==null){
            return accountFound.isPresent();
        }

        return accountFound.isPresent() && !account.getId().equals(accountFound.get().getId());

    }
}
