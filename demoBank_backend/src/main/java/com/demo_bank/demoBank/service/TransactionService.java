package com.demo_bank.demoBank.service;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DAOmodel.Transaction;
import com.demo_bank.demoBank.DTO.TransactionDTO;
import com.demo_bank.demoBank.config.Session;
import com.demo_bank.demoBank.repository.AccountRepo;
import com.demo_bank.demoBank.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
            beneficiary.setNotification(beneficiary.getNotification() + 1);
            accountRepo.save(senderAccount);
            accountRepo.save(beneficiary);


            senderTransaction.setAccount(senderAccount);
            senderTransaction.setBeneficiary(beneficiary.getFirstName() + " " + beneficiary.getLastName());
            senderTransaction.setType("Debit");
            senderTransaction.setAmount(amount);
            senderTransaction.setStatus("Success");
            senderTransaction.setNarration(narration);
            senderTransaction.setDate(DateFormatter());
            senderTransaction.setTime(TimeFormatter());
            senderTransaction.setDetails("Sent to " + beneficiary.getFirstName() + " " + beneficiary.getLastName() );
            transactionRepo.save(senderTransaction);

            beneficiaryTransaction.setAccount(beneficiary);
            beneficiaryTransaction.setType("Credit");
            beneficiaryTransaction.setAmount(amount);
            beneficiaryTransaction.setStatus("Success");
            beneficiaryTransaction.setNarration(narration);
            beneficiaryTransaction.setSender(senderAccount.getFirstName() + " " + senderAccount.getLastName());
            beneficiaryTransaction.setDate(DateFormatter());
            beneficiaryTransaction.setTime(TimeFormatter());
            beneficiaryTransaction.setDetails("From " + senderAccount.getFirstName() + " " + senderAccount.getLastName());
            transactionRepo.save(beneficiaryTransaction);


        } else if (senderCurrentBalance < amount) {
            senderTransaction.setAccount(senderAccount);
            senderTransaction.setBeneficiary(beneficiary.getFirstName() + " " + beneficiary.getLastName());
            senderTransaction.setType("Declined");
            senderTransaction.setAmount(amount);
            senderTransaction.setStatus("Unsuccessful, Insufficient funds");
            senderTransaction.setNarration(narration);
            senderTransaction.setDate(DateFormatter());
            senderTransaction.setTime(TimeFormatter());
            senderTransaction.setDetails("Declined Transaction to " + beneficiary.getFirstName() + " " + beneficiary.getLastName());
            transactionRepo.save(senderTransaction);
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


    public Object getAccountTransactionsByType(String accountNumber, String type) {
        return transactionRepo.findTransactionsByAccountNumberByType(accountNumber, type);
    }

    public Object getAllAccountTransactions(String accountNumber) {

        List<Transaction> transactionList = transactionRepo.findAllTransactionByAccount(accountNumber);
        return transactionList.stream()
                .map(transaction -> TransactionDTO.builder()
                        .transaction_id(transaction.getTransaction_id())
                        .date(transaction.getDate())
                        .amount(transaction.getAmount())
                        .status(transaction.getStatus())
                        .beneficiary(transaction.getBeneficiary())
                        .details(transaction.getDetails())
                        .narration(transaction.getNarration())
                        .sender(transaction.getSender())
                        .time(transaction.getTime())
                        .type(transaction.getType())
                        .build()
                ).collect(Collectors.toList());
    }

    public String DateFormatter(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }
    public String TimeFormatter(){
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");
        String formattedTime = currentTime.format(timeFormatter);
        return formattedTime;
    }
}
