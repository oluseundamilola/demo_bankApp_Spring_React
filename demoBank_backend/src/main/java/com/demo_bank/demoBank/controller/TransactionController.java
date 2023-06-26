package com.demo_bank.demoBank.controller;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DTO.SendMoneyRequest;
import com.demo_bank.demoBank.config.Session;
import com.demo_bank.demoBank.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private Session session;
    @Autowired
    private HttpServletRequest request;

    @PostMapping(path = "/send_money")
    public String sendMoney(@RequestBody SendMoneyRequest sendMoneyRequest){
        Account beneficiary = session.getBeneficiary();
        String sender = request.getUserPrincipal().getName();
        transactionService.sendMoney(sendMoneyRequest.getAmount(), beneficiary, sender, sendMoneyRequest.getNarration());
        return sendMoneyRequest.getAmount() + "was successfully sent to " + beneficiary.getFirstName() + beneficiary.getLastName();
    }

    @PostMapping(path = "/check_session")
    public Account checkSession(){
        return session.getBeneficiary();
    }

}
