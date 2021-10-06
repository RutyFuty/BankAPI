package ru.sberbank.dsmelnikov.bankapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.dsmelnikov.bankapi.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long id;

    private Long txSrcAccountId;

    private Long txDstAccountId;

    BigDecimal txAmount;

    private Date txDateTime;

    private TransactionStatus txStatus;

}
