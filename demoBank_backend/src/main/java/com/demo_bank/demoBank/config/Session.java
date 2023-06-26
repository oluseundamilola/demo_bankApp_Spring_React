package com.demo_bank.demoBank.config;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@SessionScope
public class Session {
    private Account beneficiary;
}
