package ru.sberbank.dsmelnikov.bankapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.dsmelnikov.bankapi.model.enums.CardStatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private Long id;

    private String cardNumber;

    private Long accountId;

    private CardStatus cardStatus;

}
