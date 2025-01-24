package com.demo_bank.demoBank.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
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
    private String transactionRef;
}
