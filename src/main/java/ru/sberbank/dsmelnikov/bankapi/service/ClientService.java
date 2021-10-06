package ru.sberbank.dsmelnikov.bankapi.service;

import ru.sberbank.dsmelnikov.bankapi.model.dto.CardDto;

import java.math.BigDecimal;
import java.util.List;

public interface ClientService {

    void issueNewCardOnAccount(CardDto cardDto);

    List<CardDto> listCards(Long accountId);

    void depositFunds(Long accountId, String amount);

    BigDecimal checkBalance(Long accountId);
}
