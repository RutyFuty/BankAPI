package ru.sberbank.dsmelnikov.bankapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.dsmelnikov.bankapi.model.enums.AccountStatus;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;

    private String accountNumber;

    private BigDecimal accountBalance;

    private AccountStatus accountStatus;

    private Long clientId;
}
