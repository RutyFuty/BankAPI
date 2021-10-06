package ru.sberbank.dsmelnikov.bankapi.service.impl.helper;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ServiceHelper {

    /**
     * Простейшая валидация номера карты
     *
     * @param cardNumber номер карты
     * @return true если номер карты проходит валидацию
     */

    public boolean validateCardNumber(String cardNumber) {
        String simplestRegex = "^[0-9]{16}$";

        Pattern pattern = Pattern.compile(simplestRegex);

        cardNumber = cardNumber
                .replaceAll("-", "")
                .replaceAll(" ", "");

        if (cardNumber.length() != 16) return false;

        Matcher matcher = pattern.matcher(cardNumber);

        return matcher.matches();
    }



}
