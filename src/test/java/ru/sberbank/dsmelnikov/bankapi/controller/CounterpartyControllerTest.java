package ru.sberbank.dsmelnikov.bankapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.AccountDaoImpl;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.CounterpartyDaoImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CounterpartyControllerTest {

    private final CounterpartyDaoImpl counterpartyDao;

    private final AccountDaoImpl accountDao;

    @LocalServerPort
    private String port;

    @Autowired
    public CounterpartyControllerTest(CounterpartyDaoImpl counterpartyDao, AccountDaoImpl accountDao) {
        this.counterpartyDao = counterpartyDao;
        this.accountDao = accountDao;
    }

    @Test
    void createCounterparty() {
        //Given
        //When
        //Then
    }

    @Test
    void listCounterparties() {
        //Given
        //When
        //Then
    }

    @Test
    void transferFundsToCounterparty() {
        //Given
        //When
        //Then
    }
}