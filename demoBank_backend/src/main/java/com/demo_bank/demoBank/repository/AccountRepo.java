package com.demo_bank.demoBank.repository;

import com.demo_bank.demoBank.DAOmodel.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    @Override
    Optional<Account> findById(Long account_id);

    Optional<Account> findOneByEmail(String email);

    Optional<Account> findOneByAccountNumber(String accountNumber);
}
