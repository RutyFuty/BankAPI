package ru.sberbank.dsmelnikov.bankapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.dsmelnikov.bankapi.model.enums.CounterpartyStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounterpartyDto {

    private Long id;

    private String name;

    private Long accountId;

    CounterpartyStatus counterpartyStatus;
}
