package com.caio.financial.service;

import com.caio.financial.entity.Account;
import com.caio.financial.entity.Transfer;
import com.caio.financial.entity.User;
import com.caio.financial.repository.AccountRepository;
import com.caio.financial.repository.TransferRepository;
import com.caio.financial.security.SecurityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final SecurityService securityService;

    public Transfer saveTransfer(Transfer transfer, UUID originAccount, UUID destinyAccount){

        User user = securityService.getLoggedUser();

        Account origin= accountRepository.
                findById(originAccount).orElseThrow(() -> new RuntimeException("Conta Inválida"));

        Account destiny =accountRepository.
                findById(destinyAccount).orElseThrow(() -> new RuntimeException("Conta Inválida"));

        validateUserOwnershipForTransfer(user,origin,destiny);
        updateBalanceAfterTransfer(origin,destiny,transfer.getValueTransfer());

        accountRepository.save(origin);
        accountRepository.save(destiny);

        transfer.setUser(user);
        transfer.setOriginAccount(origin);
        transfer.setDestinyAccount(destiny);

        return transferRepository.save(transfer);
    }

    private void validateUserOwnershipForTransfer(User user, Account origin, Account destiny){
        if(!user.getId().equals(origin.getUser().getId()) || !user.getId().equals(destiny.getUser().getId())){
            throw new RuntimeException("Conta não pertence ao usuário");
        }
    }

    private void updateBalanceAfterTransfer(Account origin, Account destiny, BigDecimal valueTransfer){

        if(valueTransfer.compareTo(origin.getCurrentBalance()) > 0){
            throw new RuntimeException("Você não possui saldo suficiente para transferir este valor");
        }
        if (valueTransfer.compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("Valor da transferência não pode ser menor ou igual a ");
        }

        BigDecimal accountOriginBalance =origin.getCurrentBalance();

        origin.setCurrentBalance(origin.getCurrentBalance().subtract(valueTransfer));

        destiny.setCurrentBalance(destiny.getCurrentBalance().add(valueTransfer));
    }

    public Optional<Transfer> findTransferById(UUID id){
       return transferRepository.findById(id);
    }

    @Transactional
    public void deleteTransfer(Transfer transfer){

        adjustBalanceOnTransferRemoval(transfer);
        transferRepository.delete(transfer);
    }

    private void adjustBalanceOnTransferRemoval(Transfer transfer){

        BigDecimal valueTransfer= transfer.getValueTransfer();

        Account origin = transfer.getOriginAccount();
        Account destiny = transfer.getDestinyAccount();

        origin.setCurrentBalance(origin.getCurrentBalance().add(valueTransfer));
        destiny.setCurrentBalance(destiny.getCurrentBalance().subtract(valueTransfer));


    }



}
