package com.caio.financial.service;

import com.caio.financial.entity.*;
import com.caio.financial.repository.AccountRepository;
import com.caio.financial.repository.CategoryRepository;
import com.caio.financial.repository.TransactionRepository;
import com.caio.financial.security.SecurityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final SecurityService securityService;
    private final AccountService accountService;



    public Optional<Transaction> findTransactionById(UUID id){
        return  transactionRepository.findById(id);
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction, UUID categoryId, UUID accountId){
        User loggedUser = securityService.getLoggedUser();

        Account account = accountRepository.findById(accountId).
                orElseThrow(() -> new RuntimeException("Inválido"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Inválido"));

        accountService.validateAccountOwnership(account,loggedUser);
        updateBalance(account, transaction.getValue(), category.getTypeCategory());

       accountRepository.save(account);

        transaction.setUser(loggedUser);
        transaction.setAccount(account);
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    private void updateBalance(Account account, BigDecimal transactionValue, TypeCategory category){

        BigDecimal currentBalance = account.getCurrentBalance();

        if(TypeCategory.RECEITA.equals(category)){
            if (currentBalance.compareTo(transactionValue) < 0) {
                throw  new RuntimeException("ERRO");
            }
            account.setCurrentBalance(currentBalance.add(transactionValue));
        }
        else {
            account.setCurrentBalance(currentBalance.subtract(transactionValue));
        }

    }


    @Transactional
    public void updateTransaction(Transaction transaction,BigDecimal transactionValueOld,UUID categoryId,TypeCategory typeCategoryOld)
    {
        User loggedUser = securityService.getLoggedUser();

        Account account= transaction.getAccount();


        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Inválido"));

        accountService.validateAccountOwnership(account,loggedUser);

        adjustAccountBalance(account,transaction.getValue(),transactionValueOld,category,typeCategoryOld);

        accountRepository.save(account);

        transaction.setUser(loggedUser);
        transaction.setAccount(account);
        transaction.setCategory(category);


        transactionRepository.save(transaction);

    }

    private void adjustAccountBalance(Account account, BigDecimal transactionValue,BigDecimal transactionValueOld, Category category,TypeCategory typeCategoryOld) {

        BigDecimal accountBalance = account.getCurrentBalance();

        if(typeCategoryOld.equals(TypeCategory.RECEITA)){
            accountBalance=accountBalance.subtract(transactionValueOld);
        }
        else{
            accountBalance=accountBalance.add(transactionValueOld);
        }

        if (category.getTypeCategory().equals(TypeCategory.RECEITA)){
            accountBalance=accountBalance.add(transactionValue);
        }
        else {
            accountBalance=accountBalance.subtract(transactionValue);
        }

        account.setCurrentBalance(accountBalance);
    }

    @Transactional
    public void removeTransaction(Transaction transaction){

        Account account= transaction.getAccount();

        adjustBalanceOnTransactionRemoval(account,transaction.getCategory().getTypeCategory(),transaction.getValue());

        accountRepository.save(account);

        transactionRepository.delete(transaction);

    }

    private void adjustBalanceOnTransactionRemoval(Account account, TypeCategory typeCategory, BigDecimal transactionValue){

        BigDecimal accountBalance= account.getCurrentBalance();

        if(typeCategory.equals(TypeCategory.RECEITA)){
          accountBalance=accountBalance.subtract(transactionValue);
        }
        else{
         accountBalance=accountBalance.add(transactionValue);
        }
        account.setCurrentBalance(accountBalance);
    }


    public Page<Transaction> findAllAccountTransactions(UUID accountId,int numberPage, int numberSize){

        Pageable page= PageRequest.of(numberPage,numberSize);
        return  transactionRepository.findByAccountId(accountId,page);
    }

    public BigDecimal geAllTotalExpenses(UUID accountId){

        List<Transaction> listExpense = transactionRepository.
                findAllByAccountId(accountId);

        BigDecimal value=BigDecimal.ZERO;

       for(Transaction transaction:listExpense){
          if(transaction.getCategory().getTypeCategory().equals(TypeCategory.DESPESA)){
              value =  value.add(transaction.getValue());
          }
       }
        return value;
    }


}
