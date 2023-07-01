package com.demo_bank.demoBank.controller;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DTO.AccountDTO;
import com.demo_bank.demoBank.DTO.BeneficiaryResponseDTO;
import com.demo_bank.demoBank.config.Session;
import com.demo_bank.demoBank.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping(path = "/add_account")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        Account newAccount = accountService.addAccount(account);
        return ResponseEntity.ok("Account created");
    }

    @GetMapping(path = "/accountDetails")
    public ResponseEntity<?> accountDetails(){
        String accountNumber = request.getUserPrincipal().getName();
        return ResponseEntity.ok(accountService.getAccountInfo(accountNumber));
    }

    @PostMapping(path = "/getBeneficiary")
    public Object getBeneficiaryByAccountNumber(@RequestBody AccountDTO accountDTO){
        Account selectedBeneficiary = accountService.getBeneficiaryByAccountNumber(accountDTO.getAccountNumber());
        BeneficiaryResponseDTO response = new BeneficiaryResponseDTO();
        response.setBeneficiary(selectedBeneficiary.getFirstName() + " " + selectedBeneficiary.getLastName());
        return response;
    }

    @PostMapping(path = "/notification_clear")
    public Object clearNotification(){
        String accountNumber = request.getUserPrincipal().getName();
        return ResponseEntity.ok(accountService.clearNofitication(accountNumber));
    }




}
