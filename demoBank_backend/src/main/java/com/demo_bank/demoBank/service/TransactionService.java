package com.demo_bank.demoBank.service;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DAOmodel.Transaction;
import com.demo_bank.demoBank.config.Session;
import com.demo_bank.demoBank.repository.AccountRepo;
import com.demo_bank.demoBank.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private Session session;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private TransactionRepo transactionRepo;


    public String sendMoney(int amount, Account beneficiary, String sender, String narration) {
        Account senderAccount = getSenderAccount(sender);
        int senderCurrentBalance = senderAccount.getBalance();

        Transaction senderTransaction = new Transaction();
        Transaction beneficiaryTransaction = new Transaction();

        if (senderCurrentBalance > amount) {
            int senderNewBalance = senderCurrentBalance - amount;
            senderAccount.setBalance(senderNewBalance);
            beneficiary.setBalance(beneficiary.getBalance() + amount);
            accountRepo.save(senderAccount);
            accountRepo.save(beneficiary);


            senderTransaction.setAccount(senderAccount);
            senderTransaction.setBeneficiary(beneficiary.getFirstName() + " " + beneficiary.getLastName());
            senderTransaction.setType("Debit");
            senderTransaction.setAmount(amount);
            senderTransaction.setStatus("Success");
            senderTransaction.setNarration(narration);
            transactionRepo.save(senderTransaction);

            beneficiaryTransaction.setAccount(beneficiary);
            beneficiaryTransaction.setType("Credit");
            beneficiaryTransaction.setAmount(amount);
            beneficiaryTransaction.setStatus("Success");
            beneficiaryTransaction.setNarration(narration);
            beneficiaryTransaction.setSender(senderAccount.getFirstName() + " " + senderAccount.getLastName());
            transactionRepo.save(beneficiaryTransaction);

        } else if (senderCurrentBalance < amount) {
            return "funds";
        } else if (senderCurrentBalance < 150) {
            return "block";
        }

        return "success";


    }

    public Account getSenderAccount(String accountNumber) {
        Account account = accountRepo.findOneByAccountNumber(accountNumber).get();
        return account;
    }
}
