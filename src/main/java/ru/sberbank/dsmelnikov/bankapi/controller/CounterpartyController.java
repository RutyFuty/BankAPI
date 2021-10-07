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
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.dsmelnikov.bankapi.model.dto.CounterpartyDto;
import ru.sberbank.dsmelnikov.bankapi.service.impl.CounterpartyServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/counterparty")
public class CounterpartyController {

    private final CounterpartyServiceImpl counterpartyService;

    @Autowired
    public CounterpartyController(CounterpartyServiceImpl counterpartyService) {
        this.counterpartyService = counterpartyService;
    }

    @PostMapping("/new/counterparty")
    public ResponseEntity<String> createCounterparty(@RequestBody CounterpartyDto counterpartyDto) {
        counterpartyService.createCounterparty(counterpartyDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Контрагент создан");
    }

    @GetMapping("/list/counterparty")
    public ResponseEntity<List<CounterpartyDto>> listCounterparties() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(counterpartyService.listCounterparties());
    }

    @PutMapping("{fromId}/transfer/{toId}/{amount}")
    public ResponseEntity<String> transferFundsToCounterparty(@PathVariable Long fromId,
                                                              @PathVariable Long toId,
                                                              @PathVariable String amount) {
        counterpartyService.transferFundsToCounterparty(fromId, toId, amount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Перевод осуществлен успешно");
    }
}
