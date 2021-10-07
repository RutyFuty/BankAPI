package ru.sberbank.dsmelnikov.bankapi.dao;

import ru.sberbank.dsmelnikov.bankapi.model.Counterparty;

import java.util.List;

public interface CounterpartyDao {

    void createCounterparty(Counterparty counterparty);

    void removeCounterparty(Long id);

    void updateCounterparty(Counterparty counterparty);

    Counterparty getCounterpartyById(Long id);

    List<Counterparty> listCounterparties();
}
