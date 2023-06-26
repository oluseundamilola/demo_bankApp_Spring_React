package com.demo_bank.demoBank.repository;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DAOmodel.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    @Override
    Optional<Transaction> findById(Long transaction_id);
}
