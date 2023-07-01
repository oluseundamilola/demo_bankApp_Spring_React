package com.demo_bank.demoBank.service;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DTO.AccountDTO;
import com.demo_bank.demoBank.config.Session;
import com.demo_bank.demoBank.repository.AccountRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepo accountRepo;


    public Account addAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(10000);
        accountRepo.save(account);
        return account;
    }

    public Object getAccountInfo(String accountNumber) {
        Account account = accountRepo.findOneByAccountNumber(accountNumber).get();
        return account;
    }
    public Account getBeneficiaryByAccountNumber(String accountNumber){
        Account beneficiaryAccount = accountRepo.findOneByAccountNumber(accountNumber).get();
        return beneficiaryAccount;
    }


    private static String generateAccountNumber() {
        Random rand = new Random();
        long upperBound = 10000000000L; // 10^10
        long lowerBound = 1000000000L; // 10^9
        long accountNumberGenerated = (long) rand.nextInt((int) (upperBound - lowerBound)) + lowerBound;
        return String.valueOf(accountNumberGenerated);
        //get an account, then get account numbers
        //if accountNumberGenerated = any account number
        //generated another
    }

    public Object clearNofitication(String accountNumber) {
        Account account = accountRepo.findOneByAccountNumber(accountNumber).get();
        account.setNotification(0);
        accountRepo.save(account);
        return "Cleared";
    }
}
