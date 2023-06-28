package com.demo_bank.demoBank.controller;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.DTO.BeneficiaryResponseDTO;
import com.demo_bank.demoBank.DTO.SendMoneyRequest;
import com.demo_bank.demoBank.config.Session;
import com.demo_bank.demoBank.service.AccountService;
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
    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/send_money")
    public String sendMoney(@RequestBody SendMoneyRequest sendMoneyRequest) {
        String sender = request.getUserPrincipal().getName();
        Account beneficiary = accountService.getBeneficiaryByAccountNumber(sendMoneyRequest.getBeneficiaryAccountNumber());
        return (transactionService.sendMoney(sendMoneyRequest.getAmount(), beneficiary, sender, sendMoneyRequest.getNarration()));
    }

    @PostMapping(path = "/check_session")
    public ResponseEntity<Object> checkSession() {
        Account beneficiary = session.getBeneficiary();
        if(beneficiary == null){
            String errorMessage = "No Beneficiary selected";
            return ResponseEntity.badRequest().body(errorMessage);
        }
        else{
            BeneficiaryResponseDTO response = new BeneficiaryResponseDTO();
            response.setBeneficiary(beneficiary.getFirstName() + " " + beneficiary.getLastName());
            return ResponseEntity.ok(response);
        }
    }

}
