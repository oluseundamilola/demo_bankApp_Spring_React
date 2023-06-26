package com.demo_bank.demoBank.service;

import com.demo_bank.demoBank.DAOmodel.Account;
import com.demo_bank.demoBank.config.ConvertUserInfoTOUserDetails;
import com.demo_bank.demoBank.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        Optional<Account> accountInfo = accountRepo.findOneByAccountNumber(accountNumber);
        return accountInfo.map(ConvertUserInfoTOUserDetails::new)
                .orElseThrow( () -> new UsernameNotFoundException("Account not found " + accountNumber));
    }
}
