package ru.sberbank.dsmelnikov.bankapi.service.helper;

import org.springframework.stereotype.Component;
import ru.sberbank.dsmelnikov.bankapi.handler.exception.ApplicationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public BigDecimal transformToBigDecimal(String amount) {
        BigDecimal biDecAmount;
        try {
            biDecAmount = new BigDecimal(amount)
                    .setScale(2, RoundingMode.HALF_EVEN);
        } catch (NumberFormatException e) {
            throw new ApplicationException("Невозможно получить число из предоставленного");
        }

        //Проверка вносимой суммы на ноль и отрицательное значение
        if (biDecAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApplicationException("Некорректная сумма для внесения на счет, <= 0");
        }
        return biDecAmount;
    }

}
