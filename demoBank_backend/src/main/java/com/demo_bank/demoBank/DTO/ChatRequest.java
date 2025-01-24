package com.demo_bank.demoBank.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatRequest {
    @JsonProperty("userMessage")
    private String userMessage;
}
