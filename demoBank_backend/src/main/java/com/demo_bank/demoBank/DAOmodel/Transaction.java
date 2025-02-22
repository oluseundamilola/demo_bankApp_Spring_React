package com.demo_bank.demoBank.DAOmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    private String narration;
    private String details;
    private String beneficiary;
    private String type;
    private String status;
    private int amount;
    private String sender;
    private String date;
    private String time;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;
    private String transactionRef;
    private boolean dispute;
    private String disputeStatus;
    private boolean reversed;
    private boolean submittedForReversal;

}

