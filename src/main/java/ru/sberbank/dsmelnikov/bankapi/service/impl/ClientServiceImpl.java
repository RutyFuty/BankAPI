package ru.sberbank.dsmelnikov.bankapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.AccountDaoImpl;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.CardDaoImpl;
import ru.sberbank.dsmelnikov.bankapi.dao.impl.ClientDaoImpl;
import ru.sberbank.dsmelnikov.bankapi.handler.exception.ApplicationException;
import ru.sberbank.dsmelnikov.bankapi.model.Account;
import ru.sberbank.dsmelnikov.bankapi.model.Card;
import ru.sberbank.dsmelnikov.bankapi.model.dto.CardDto;
import ru.sberbank.dsmelnikov.bankapi.model.enums.CardStatus;
import ru.sberbank.dsmelnikov.bankapi.service.ClientService;
import ru.sberbank.dsmelnikov.bankapi.service.impl.helper.ServiceHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDaoImpl clientDao;

    private final AccountDaoImpl accountDao;

    private final CardDaoImpl cardDao;

    private final ServiceHelper serviceHelper;

    @Autowired
    public ClientServiceImpl(ClientDaoImpl clientDao, AccountDaoImpl accountDao, CardDaoImpl cardDao, ServiceHelper serviceHelper) {
        this.clientDao = clientDao;
        this.accountDao = accountDao;
        this.cardDao = cardDao;
        this.serviceHelper = serviceHelper;
    }

    /**
     * Выпуск новой карты по счету.
     * Статус новой карты - NEW.
     *
     * @param cardDto данные формируемой карты
     *                (её номер и id аккаунта,
     *                к которой она принадлежит)
     */

    @Override
    public void issueNewCardOnAccount(CardDto cardDto) {
        //Проверка DTO карты (cardDto) на null
        if (cardDto == null)
            throw new ApplicationException("Некорректные данные карты, null");

        //Проверка на валидность номера карты
        if (!serviceHelper.validateCardNumber(cardDto.getCardNumber()))
            throw new ApplicationException("Некорректный номер карты");

        //Получение счета из бд по id
        Account account = accountDao.getAccountById(cardDto.getAccountId());

        //Проверка account на null
        if (account == null) {
            throw new ApplicationException("Некорректные данные аккаунта, null");
        }

        //Формирование карты из полученных данных
        Card card = Card.builder()
                .cardNumber(cardDto.getCardNumber())
                .account(account)
                .cardStatus(CardStatus.NEW)
                .build();
        //Внесение карты в бд

        cardDao.createCard(card);
    }

    /**
     * Просмотр списка карт по счету
     *
     * @param accountId id счета
     * @return Список карт счета
     */

    @Override
    public List<CardDto> listCards(Long accountId) {
        if (accountId <= 0)
            throw new ApplicationException("Некорректная id счета, <= 0");

        //Получение всех карт, принадлежащих счету
        List<Card> accountCardList = cardDao.listCardsOnAccount(accountId);

        //Создание пустого результирующего листа с DTO карт
        List<CardDto> resultCardList = new ArrayList<>();

        //Проход по всем картам на счету, формирование DTO и внесение их в результирующий лист
        for (Card card : accountCardList) {
            resultCardList.add(CardDto.builder()
                    .id(card.getId())
                    .cardStatus(card.getCardStatus())
                    .accountId(card.getAccount().getId())
                    .cardNumber(card.getCardNumber())
                    .build());
        }

        //Возврат результирующего листа
        return resultCardList;
    }

    /**
     * Внесение средств на счет
     *
     * @param accountId id счета
     * @param amount    сумма, вносимая на счет
     */

    @Override
    public void depositFunds(Long accountId, String amount) {
        //Получение счета из бд по id
        Account account = accountDao.getAccountById(accountId);

        //Проверка account и amount на null
        if (account == null)
            throw new ApplicationException("Некорректные данные аккаунта, null");
        if (amount.equals(""))
            throw new ApplicationException("Некоректная сумма для внесения на счет, empty");

        //Определение старой суммы на счету
        BigDecimal oldAmount;

        //Формирование суммы средств для внесения на счет
        BigDecimal depositAmount;

        try {
            oldAmount = account.getAccountBalance();
        } catch (NumberFormatException e) {
            throw new ApplicationException("Невозможно получить число из бд");
        }

        try {
            depositAmount = new BigDecimal(amount)
                    .setScale(2, RoundingMode.HALF_EVEN);
        } catch (NumberFormatException e) {
            throw new ApplicationException("Невозможно получить число из предоставленного");
        }

        //Проверка вносимой суммы на ноль и отрицательное значение
        if (depositAmount.compareTo(BigDecimal.ZERO) <= 0)
            throw new ApplicationException("Некорректная сумма для внесения на счет, <= 0");

        //Суммирование вносимого депозита и старого значения
        account.setAccountBalance(oldAmount.add(depositAmount));

        //Обновление данных счета
        accountDao.updateAccount(account);
    }

    /**
     * Проверка баланса на счету
     *
     * @param accountId id счета
     * @return Баланс на счету
     */

    @Override
    public BigDecimal checkBalance(Long accountId) {
        Account account;

        //Проверка account на null
        try {
            account = accountDao.getAccountById(accountId);

            //Возврат баланса на счету
            return account.getAccountBalance();
        } catch (NullPointerException e) {
            throw new ApplicationException("Некорректный id аккаунта, null");
        }

    }

}
