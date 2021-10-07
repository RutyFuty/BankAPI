package ru.sberbank.dsmelnikov.bankapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.AccountDaoImpl;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.CounterpartyDaoImpl;
import ru.sberbank.dsmelnikov.bankapi.handler.exception.ApplicationException;
import ru.sberbank.dsmelnikov.bankapi.model.Account;
import ru.sberbank.dsmelnikov.bankapi.model.Counterparty;
import ru.sberbank.dsmelnikov.bankapi.model.dto.CounterpartyDto;
import ru.sberbank.dsmelnikov.bankapi.model.enums.CounterpartyStatus;
import ru.sberbank.dsmelnikov.bankapi.service.CounterpartyService;
import ru.sberbank.dsmelnikov.bankapi.service.helper.ServiceHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CounterpartyServiceImpl implements CounterpartyService {

    private final CounterpartyDaoImpl counterpartyDao;

    private final AccountDaoImpl accountDao;

    private final ServiceHelper serviceHelper;

    @Autowired
    public CounterpartyServiceImpl(CounterpartyDaoImpl counterpartyDao, AccountDaoImpl accountDao, ServiceHelper serviceHelper) {
        this.counterpartyDao = counterpartyDao;
        this.accountDao = accountDao;
        this.serviceHelper = serviceHelper;
    }

    @Override
    public void createCounterparty(CounterpartyDto counterPartyDto) {
        //Проверка DTO контрагента (cardDto) на null
        if (counterPartyDto == null) {
            throw new ApplicationException("Некорректные данные контрагента, null");
        }

        //Получение счета из бд по id
        Account account = accountDao.getAccountById(counterPartyDto.getAccountId());

        //Проверка account на null
        if (account == null) {
            throw new ApplicationException("Некорректные данные счета, null");
        }

        //Формирование контрагента из полученных данных
        Counterparty counterparty = Counterparty
                .builder()
                .name(counterPartyDto.getName())
                .counterpartyStatus(CounterpartyStatus.NEW)
                .account(account)
                .build();

        //Внесение контрагента в бд
        counterpartyDao.createCounterparty(counterparty);
    }

    @Override
    public List<CounterpartyDto> listCounterparties() {
        //Получение всех контрагентов из бд
        List<Counterparty> counterpartyList = counterpartyDao.listCounterparties();

        //Создание пустого результирующего листа с DTO контрагентов
        List<CounterpartyDto> counterpartyDtoList = new ArrayList<>();

        //Проход по всем контрагентам, формирование DTO и внесение их в результирующий лист
        for (Counterparty counterparty : counterpartyList) {
            counterpartyDtoList.add(CounterpartyDto
                    .builder()
                            .id(counterparty.getId())
                            .accountId(counterparty.getAccount().getId())
                            .counterpartyStatus(counterparty.getCounterpartyStatus())
                            .name(counterparty.getName())
                    .build());
        }

        //Возврат результирующего листа
        return counterpartyDtoList;
    }

    @Override
    public void transferFundsToCounterparty(Long fromId,
                                            Long toId,
                                            String amount) {
        //Получение контрагентов из базы данных
        Counterparty counterpartyFrom = counterpartyDao.getCounterpartyById(fromId);
        Counterparty counterpartyTo = counterpartyDao.getCounterpartyById(toId);

        //Проверка контрагентов на null
        if (counterpartyFrom == null) {
            throw new ApplicationException("Контрагента - отправителя не существует");
        }
        if (counterpartyTo == null) {
            throw new ApplicationException("Контрагента - получателя не существует");
        }

        //Получение счетов контрагентов
        Account accountFrom = accountDao
                .getAccountById(counterpartyFrom
                        .getAccount()
                        .getId());
        Account accountTo = accountDao
                .getAccountById(counterpartyTo
                        .getAccount()
                        .getId());

        //Получение баланса отправляющей стороны
        BigDecimal accFromBalance = accountFrom.getAccountBalance();

        //Проверка баланса отправляющей стороны на ноль
        if (accFromBalance.compareTo(BigDecimal.ZERO) == 0) {
            throw new ApplicationException("Транзакция невозможна, недостаточно средств");
        }

        //Формирование баланса получателя
        BigDecimal accToBalance = accountTo.getAccountBalance();

        //Формирование переводимой суммы
        BigDecimal transferAmount = serviceHelper.transformToBigDecimal(amount);

        //Перевод средств
        accountFrom.setAccountBalance(accFromBalance.subtract(transferAmount));
        accountTo.setAccountBalance(accToBalance.add(transferAmount));

        //Проверка возможного баланса отправляющей стороны на отрицательное значение или ноль
        if (accountFrom.getAccountBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApplicationException("Транзакция невозможна, недостаточно средств");
        }

        //Обновление данных аккаунтов
        accountDao.updateAccount(accountFrom);
        accountDao.updateAccount(accountTo);
    }
}
