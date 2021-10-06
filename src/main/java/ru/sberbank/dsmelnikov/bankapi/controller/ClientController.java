package ru.sberbank.dsmelnikov.bankapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.dsmelnikov.bankapi.model.dto.CardDto;
import ru.sberbank.dsmelnikov.bankapi.service.impl.ClientServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/new_card")
    public ResponseEntity<Object> issueNewCardOnAccount(@RequestBody CardDto cardDto) {
        clientService.issueNewCardOnAccount(cardDto);
        return ResponseEntity.status(HttpStatus.OK).body("Новая карта успешно создана");
    }

    @GetMapping(path = "/list_cards/{accountId}")
    public ResponseEntity<List<CardDto>> listCards(@PathVariable Long accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.listCards(accountId));
    }

    @PutMapping(path = "/transaction/{accountId}")
    public ResponseEntity<Object> depositFunds(@PathVariable Long accountId, @RequestParam String amount) {
        clientService.depositFunds(accountId, amount);
        return ResponseEntity.status(HttpStatus.OK).body("Средства внесены");
    }

    @GetMapping(path = "/balance/{accountId}")
    public ResponseEntity<BigDecimal> checkBalance(@PathVariable Long accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.checkBalance(accountId));
    }
}
