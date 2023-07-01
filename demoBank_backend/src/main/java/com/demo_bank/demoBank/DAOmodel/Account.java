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
@Table(name = "account")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private String accountNumber;
    private int balance;
    private int notification;

    private String password;
}
