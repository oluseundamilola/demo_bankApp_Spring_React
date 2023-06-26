package com.demo_bank.demoBank.controller;

import com.demo_bank.demoBank.DTO.AuthRequestDTO;
import com.demo_bank.demoBank.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/login")
public class LoginController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getAccountNumber(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequestDTO.getAccountNumber());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}
