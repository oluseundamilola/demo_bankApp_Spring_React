package com.demo_bank.demoBank.config;

import com.demo_bank.demoBank.DAOmodel.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ConvertUserInfoTOUserDetails implements UserDetails {

    private String accountNumber;
    private String password;

    public ConvertUserInfoTOUserDetails(Account account){
        accountNumber = account.getAccountNumber();
        password = account.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return accountNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
