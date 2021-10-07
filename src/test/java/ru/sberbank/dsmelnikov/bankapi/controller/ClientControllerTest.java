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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {

    private final CardDaoImpl cardDao;

    private final AccountDaoImpl accountDao;

    private final RestTemplate restTemplate;

    @LocalServerPort
    private String port;

    @Autowired
    public ClientControllerTest(CardDaoImpl cardDao,
                                AccountDaoImpl accountDao,
                                RestTemplate restTemplate) {
        this.cardDao = cardDao;
        this.accountDao = accountDao;
        this.restTemplate = restTemplate;
    }

    @Test
    void issueNewCardOnAccount_WithPlausibleSpacedNumberAndAccountId_ShouldWorkProperly() {
        //Given
        CardDto cardDto = CardDto.builder()
                .accountId(1L)
                .cardNumber("0000 0000 0000 0000")
                .build();

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/new_card/";

        //When
        ResponseEntity<String> response = restTemplate
                .postForEntity(url, cardDto, String.class);

        //Then
        assertEquals(OK, response.getStatusCode());
        assertEquals("Новая карта успешно создана", response.getBody());
    }

    @Test
    void issueNewCardOnAccount_WithPlausibleDashedNumberAndAccountId_ShouldWorkProperly() {
        //Given
        CardDto cardDto = CardDto.builder()
                .accountId(1L)
                .cardNumber("1234-1234-7894-3283")
                .build();

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/new_card/";

        //When
        ResponseEntity<String> response = restTemplate
                .postForEntity(url, cardDto, String.class);

        //Then
        assertEquals(OK, response.getStatusCode());
        assertEquals("Новая карта успешно создана", response.getBody());
    }

    @Test()
    void issueNewCardOnAccount_WithBadCardNumberAndPlausibleAccountId_ShouldFail() {
        //Given
        CardDto cardDto = CardDto.builder()
                .accountId(1L)
                .cardNumber("Not A Number")
                .build();

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/new_card/";

        //When and Then
        Throwable throwable = assertThrows(HttpServerErrorException.class, () ->
                restTemplate.postForEntity(url, cardDto, Void.class));

        assertEquals("500 : [Некорректный номер карты]", throwable.getMessage());
    }

    @Test
    void issueNewCardOnAccount_WithPlausibleCardNumberAndNotExistingAccountId_ShouldFail() {
        //Given
        CardDto cardDto = CardDto.builder()
                .accountId(1000000000L)
                .cardNumber("1234 1234 7894 3283")
                .build();

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/new_card/";

        //When and Then
        Throwable throwable = assertThrows(HttpServerErrorException.class, () ->
                restTemplate.postForEntity(url, cardDto, Void.class));

        assertEquals("500 : [Некорректные данные счета, null]", throwable.getMessage());
    }

    @Test
    void listCards_WithExistingAccountId_ShouldWorkProperlyAndCardsQuantityBeCorrect() {
        //Given
        long accountId = 1L;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/list_cards/" + accountId;

        List<Card> cardsList = cardDao.listCardsOnAccount(1L);

        //When
        ResponseEntity<CardDto[]> response = restTemplate
                .getForEntity(url, CardDto[].class);

        //Then
        assertEquals(OK, response.getStatusCode());
        assertEquals(cardsList.size(), response.getBody().length);
    }

    @Test
    void listCards_WithExistingAccountId_ShouldWorkProperlyAndFirstCardExist() {
        //Given
        long accountId = 1L;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/list_cards/" + accountId;

        List<Card> cardsList = cardDao.listCardsOnAccount(1L);

        //When
        ResponseEntity<CardDto[]> response = restTemplate
                .getForEntity(url, CardDto[].class);

        //Then
        assertEquals(OK, response.getStatusCode());
        assertEquals(cardsList.get(0).getCardNumber(), response.getBody()[0].getCardNumber());
    }

    @Test
    void listCards_WithNegativeAccountId_ShouldFail() {
        //Given
        long accountId = -20L;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/list_cards/" + accountId;

        //When and Then
        Throwable throwable = assertThrows(HttpServerErrorException.class, () ->
                restTemplate.getForEntity(url, Void.class));

        assertEquals("500 : [Некорректная id счета, <= 0]", throwable.getMessage());
    }

    @Test
    void depositFunds_WithExistingAccountIdAndCorrectAmount_ShouldWorkProperly() {
        //Given
        long accountId = 1L;
        long amount = 1;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/transaction/" + accountId + "?amount=" + amount;

        //When
        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.PUT, null, String.class);

        //Then
        assertEquals(OK, response.getStatusCode());
        assertEquals("Средства внесены", response.getBody());

    }

    @Test
    void depositFunds_WithExistingAccountIdAndCorrectAmount_ShouldWorkProperlyAndAmountUpdated() {
        //Given
        long accountId = 1L;
        String amount = "17658.789";

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/transaction/" + accountId + "?amount=" + amount;

        Account account = accountDao.getAccountById(accountId);

        String oldAmount = account.getAccountBalance().toString();

        //When
        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.PUT, null, String.class);

        Account accountUpd = accountDao.getAccountById(accountId);

        String newAmount = accountUpd.getAccountBalance().toString();

        //Then
        assertEquals(OK, response.getStatusCode());
        assertEquals("Средства внесены", response.getBody());
        assertNotEquals(newAmount, oldAmount);
    }

    @Test
    void depositFunds_WithUnexpectedAmount_ShouldFail() {
        //Given
        long accountId = 1L;
        String amount = "NOT A NUMBER";

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/transaction/" + accountId + "?amount=" + amount;

        //When and Then
        Throwable throwable = assertThrows(HttpServerErrorException.class, () ->
                restTemplate.exchange(url, HttpMethod.PUT, null, Void.class));

        assertEquals("500 : [Невозможно получить число из предоставленного]", throwable.getMessage());
    }

    @Test
    void depositFunds_WithNegativeAmount_ShouldFail() {
        //Given
        long accountId = 1L;
        String amount = "-1000";

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/transaction/" + accountId + "?amount=" + amount;

        //When and Then
        Throwable throwable = assertThrows(HttpServerErrorException.class, () ->
                restTemplate.exchange(url, HttpMethod.PUT, null, Void.class));

        assertEquals("500 : [Некорректная сумма для внесения на счет, <= 0]", throwable.getMessage());

    }

    @Test
    void checkBalance_WithExistingAccountId_ShouldWorkProperlyAndAmountBeCorrect() {
        //Given
        long accountId = 1L;

        String baseUrl = "http://localhost:" + port + "/api/client";
        String url = baseUrl + "/balance/" + accountId;

        Account account = accountDao.getAccountById(accountId);
        String amount = account.getAccountBalance().toString();

        //When
        ResponseEntity<String> response = restTemplate
                .getForEntity(url, String.class);

        //Then
        assertEquals(OK, response.getStatusCode());
        assertEquals(amount, response.getBody());
    }
}