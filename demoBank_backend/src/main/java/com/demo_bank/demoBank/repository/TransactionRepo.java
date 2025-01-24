package com.demo_bank.demoBank.repository;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DAOmodel.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    @Override
    Optional<Transaction> findById(Long transaction_id);

    @Query("select t from Transaction t where t.account.accountNumber = :accountNumber and t.type like :type")
    List<Transaction> findTransactionsByAccountNumberByType(String accountNumber, String type);

    @Query("select t from Transaction t where t.account.accountNumber = :accountNumber order by t.transaction_id desc")
    List<Transaction> findAllTransactionByAccount(String accountNumber);

    @Query("SELECT t FROM Transaction t WHERE t.transactionRef = :transactionRef ORDER BY t.transaction_id DESC")
    Transaction findByTransactionRef(String transactionRef);


}
