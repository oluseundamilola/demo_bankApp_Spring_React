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
public class AccountDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Long balance;
    private String password;
    private String accountNumber;
}
