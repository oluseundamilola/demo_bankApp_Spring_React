package com.demo_bank.demoBank.service;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DAOmodel.Transaction;
import com.demo_bank.demoBank.DTO.TransactionDTO;
import com.demo_bank.demoBank.config.Session;
import com.demo_bank.demoBank.repository.AccountRepo;
import com.demo_bank.demoBank.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
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
        String transactionRef = generateTransactionRef();

        if (senderCurrentBalance > amount) {
            //simulate a failed transaction here
            if(amount == 5000){
                int senderNewBalance = senderCurrentBalance - amount;
                senderAccount.setBalance(senderNewBalance);
                accountRepo.save(senderAccount);

                senderTransaction.setAccount(senderAccount);
                senderTransaction.setBeneficiary(beneficiary.getFirstName() + " " + beneficiary.getLastName());
                senderTransaction.setType("Debit");
                senderTransaction.setAmount(amount);
                senderTransaction.setStatus("FAILED");
                senderTransaction.setNarration(narration);
                senderTransaction.setDate(DateFormatter());
                senderTransaction.setTime(TimeFormatter());
                senderTransaction.setDetails("Transaction failed due to network issues");
                senderTransaction.setTransactionRef(transactionRef);
                transactionRepo.save(senderTransaction);
                return "failed";
            }
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
            senderTransaction.setTransactionRef(transactionRef);
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
            beneficiaryTransaction.setTransactionRef(generateTransactionRef());
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
                        .transactionRef(transaction.getTransactionRef())
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

    public Transaction getTransactionByRef( String transactionRef) {
        Transaction byTransactionRef = transactionRepo.findByTransactionRef(transactionRef);
        return byTransactionRef;
    }

    public String preformReversal(String transactionRef){
        Transaction byTransactionRef = transactionRepo.findByTransactionRef(transactionRef);
        Account account = byTransactionRef.getAccount();
        if(byTransactionRef.getAmount() == 5000 && !byTransactionRef.isReversed()){
            //perform reversal
            int balance = account.getBalance();
            int updatedBalance = balance + byTransactionRef.getAmount();
            account.setBalance(updatedBalance);
            account.setNotification(account.getNotification() + 1);
            accountRepo.save(account);

            byTransactionRef.setSubmittedForReversal(true);
            byTransactionRef.setReversed(true);
            byTransactionRef.setType("Reversed");
            transactionRepo.save(byTransactionRef);
            return "success";
        }
        return "failed";
    }

    public void submitDispute(String transactionId) {
        Transaction byTransactionRef = transactionRepo.findByTransactionRef(transactionId);
        byTransactionRef.setDispute(true);
        byTransactionRef.setDisputeStatus("PENDING");
        transactionRepo.save(byTransactionRef);
    }

    public Object reverseFailedTransaction(String transactionRef) {
        Transaction transactionByRef = getTransactionByRef(transactionRef);
        if(transactionByRef.getStatus().equalsIgnoreCase("FAILED")){
            //perform reversal
            return preformReversal(transactionRef);

        }

        return "This transaction is not suitable for a revesal";
    }


    public static String generateTransactionRef() {
        // Generate the first part: 3 random digits
        String prefix = String.format("%03d", new Random().nextInt(1000));

        // Generate a random 4-character alphanumeric string (lowercase letters only)
        String randomString = generateRandomString(4);

        // Generate the last part: 4 random digits
        String year = String.format("%04d", new Random().nextInt(10000));

        // Combine all parts to form the transaction reference
        return String.format("%s-%s-%s", prefix, randomString, year);
    }
    private static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString(); // Return the generated string
    }

}
