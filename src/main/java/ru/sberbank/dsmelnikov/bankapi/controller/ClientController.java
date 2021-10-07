package ru.sberbank.dsmelnikov.bankapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.dsmelnikov.bankapi.model.dto.CardDto;
import ru.sberbank.dsmelnikov.bankapi.service.impl.ClientServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Rest-контроллер, предоставляющий API для клиента (физического лица)
 * <p>
 * ...api/client/...
 */

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    /**
     * Выпуск новой карты по счету
     * <p>
     * Метод обрабатывает Post-запросы (.../new_card)
     *
     * @param cardDto данные формируемой карты
     *                (её номер и id аккаунта,
     *                к которой она принадлежит)
     *                в виде json
     * @return http-ответ со строкой в теле, обозначающей результат запроса
     */

    @PostMapping("/new_card")
    public ResponseEntity<String> issueNewCardOnAccount(@RequestBody CardDto cardDto) {
        clientService.issueNewCardOnAccount(cardDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Новая карта успешно создана");
    }

    /**
     * Просмотр списка карт по счету
     * <p>
     * Метод обрабатывает Get-запросы (.../list_cards/{accountId})
     *
     * @param accountId id счета
     * @return http-ответ со списком карт определенного счета в теле
     */

    @GetMapping(path = "/list_cards/{accountId}")
    public ResponseEntity<List<CardDto>> listCards(@PathVariable Long accountId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.listCards(accountId));
    }

    /**
     * Внесение средств на счет
     * <p>
     * Метод обрабатывает Put-запросы (.../transaction/{accountId}?amount=...)
     *
     * @param accountId id счета
     * @param amount    сумма, вносимая на счет
     * @return http-ответ со строкой в теле, обозначающей результат запроса
     */

    @PutMapping(path = "/transaction/{accountId}")
    public ResponseEntity<String> depositFunds(@PathVariable Long accountId,
                                               @RequestParam String amount) {
        clientService.depositFunds(accountId, amount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Средства внесены");
    }

    /**
     * Проверка баланса на счету
     * <p>
     * Метод обрабатывает Get-запросы (.../balance/{accountId})
     *
     * @param accountId id счета
     * @return http-ответ с суммой на счету
     */

    @GetMapping(path = "/balance/{accountId}")
    public ResponseEntity<BigDecimal> checkBalance(@PathVariable Long accountId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.checkBalance(accountId));
    }
}
