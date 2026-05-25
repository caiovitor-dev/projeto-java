package com.caio.financial.repository;

import com.caio.financial.entity.Transaction;
import com.caio.financial.entity.TypeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findByAccountId(UUID accountId, Pageable pageable);



    List<Transaction> findAllByAccountId(UUID accountId);

}
