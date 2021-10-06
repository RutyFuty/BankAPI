package ru.sberbank.dsmelnikov.bankapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.AccountDaoImpl;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.CardDaoImpl;
import ru.sberbank.dsmelnikov.bankapi.model.Account;
import ru.sberbank.dsmelnikov.bankapi.model.Card;
import ru.sberbank.dsmelnikov.bankapi.model.dto.CardDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {

    @Autowired
    private CardDaoImpl cardDao;

    @Autowired
    private AccountDaoImpl accountDao;

    @LocalServerPort
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void issueNewCardOnAccountOk1() throws Exception {
        CardDto cardDto = CardDto.builder()
                .accountId(1L)
                .cardNumber("0000 0000 0000 0000")
                .build();

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/new_card/";

        ResponseEntity<String> response = restTemplate
                .postForEntity(url, cardDto, String.class);

        assertEquals(OK, response.getStatusCode());

        assertEquals("Новая карта успешно создана", response.getBody());
    }

    @Test
    void issueNewCardOnAccountOk2() throws Exception {
        CardDto cardDto = CardDto.builder()
                .accountId(1L)
                .cardNumber("1234-1234-7894-3283")
                .build();

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/new_card/";

        ResponseEntity<String> response = restTemplate
                .postForEntity(url, cardDto, String.class);

        assertEquals(OK, response.getStatusCode());

        assertEquals("Новая карта успешно создана", response.getBody());
    }

    @Test
    void issueNewCardOnAccountBad1() throws Exception {
        CardDto cardDto = CardDto.builder()
                .accountId(1L)
                .cardNumber("Not A Number")
                .build();

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/new_card/";

        try {
            restTemplate.postForEntity(url, cardDto, Void.class);
        } catch (HttpServerErrorException e) {
            assertEquals("500 : [Некорректный номер карты]", e.getMessage());
        }
    }

    @Test
    void issueNewCardOnAccountBad2() throws Exception {
        CardDto cardDto = CardDto.builder()
                .accountId(1000000000L)
                .cardNumber("1234 1234 7894 3283")
                .build();

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/new_card/";

        try {
            restTemplate.postForEntity(url, cardDto, Void.class);
        } catch (HttpServerErrorException e) {
            assertEquals("500 : [Некорректные данные аккаунта, null]", e.getMessage());
        }
    }

    @Test
    void listCardsOk1() {
        long accountId = 1L;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/list_cards/" + accountId;

        ResponseEntity<CardDto[]> response = restTemplate
                .getForEntity(url, CardDto[].class);

        assertEquals(OK, response.getStatusCode());

        List<Card> cardsList = cardDao.listCardsOnAccount(1L);

        assertEquals(cardsList.size(), response.getBody().length);
    }

    @Test
    void listCardsOk2() {
        long accountId = 1L;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/list_cards/" + accountId;

        ResponseEntity<CardDto[]> response = restTemplate
                .getForEntity(url, CardDto[].class);

        assertEquals(OK, response.getStatusCode());

        List<Card> cardsList = cardDao.listCardsOnAccount(1L);

        assertEquals(cardsList.get(0).getCardNumber(), response.getBody()[0].getCardNumber());
    }

    @Test
    void listCardsBad1() {
        long accountId = -20L;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/list_cards/" + accountId;

        try {
            restTemplate.getForEntity(url, Void.class);

        } catch (HttpServerErrorException e) {
            assertEquals("500 : [Некорректная id счета, <= 0]", e.getMessage());
        }
    }

    @Test
    void depositFundsOk1() {
        long accountId = 1L;
        long amount = 1;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/transaction/" + accountId + "?amount=" + amount;

        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.PUT, null, String.class);

        assertEquals(OK, response.getStatusCode());

        assertEquals("Средства внесены", response.getBody());

    }

    @Test
    void depositFundsOk2() {
        long accountId = 1L;
        String amount = "17658.789";

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/transaction/" + accountId + "?amount=" + amount;

        Account account = accountDao.getAccountById(accountId);

        String oldAmount = account.getAccountBalance().toString();

        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.PUT, null, String.class);

        Account accountUpd = accountDao.getAccountById(accountId);

        String newAmount = accountUpd.getAccountBalance().toString();

        assertEquals(OK, response.getStatusCode());

        assertEquals("Средства внесены", response.getBody());

        assertNotEquals(newAmount, oldAmount);
    }

    @Test
    void depositFundsBad1() {
        long accountId = 1L;
        String amount = "NOT A NUMBER";

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/transaction/" + accountId + "?amount=" + amount;

        try {
            restTemplate.exchange(url, HttpMethod.PUT, null, Void.class);
        } catch (HttpServerErrorException e) {
            assertEquals("500 : [Невозможно получить число из предоставленного]", e.getMessage());
        }
    }

    @Test
    void depositFundsBad2() {
        long accountId = 1L;
        String amount = "-1000";

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/transaction/" + accountId + "?amount=" + amount;

        try {
            restTemplate.exchange(url, HttpMethod.PUT, null, Void.class);
        } catch (HttpServerErrorException e) {
            assertEquals("500 : [Некорректная сумма для внесения на счет, <= 0]", e.getMessage());
        }
    }

    @Test
    void checkBalanceOk1() {
        long accountId = 1L;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/balance/" + accountId;

        ResponseEntity<String> response = restTemplate
                .getForEntity(url, String.class);

        assertEquals(OK, response.getStatusCode());

        Account account = accountDao.getAccountById(accountId);

        String amount = account.getAccountBalance().toString();

        assertEquals(amount, response.getBody());
    }
}