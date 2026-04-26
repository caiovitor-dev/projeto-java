package com.caio.financial.repository;

import com.caio.financial.entity.Account;
import com.caio.financial.entity.TypeAccount;
import com.caio.financial.entity.TypeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUserIdAndTypeAccount(UUID userId, TypeAccount typeAccount);
}
