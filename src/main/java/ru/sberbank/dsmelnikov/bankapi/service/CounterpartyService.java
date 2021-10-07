package ru.sberbank.dsmelnikov.bankapi.service;

import ru.sberbank.dsmelnikov.bankapi.model.dto.CounterpartyDto;

import java.math.BigDecimal;
import java.util.List;

public interface CounterpartyService {

    void createCounterparty(CounterpartyDto counterPartyDto);

    List<CounterpartyDto> listCounterparties();

    void transferFundsToCounterparty(Long fromId, Long toId, String amount);
}
