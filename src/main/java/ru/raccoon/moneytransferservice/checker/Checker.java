package ru.raccoon.moneytransferservice.checker;

import ru.raccoon.moneytransferservice.exception.BadRequestException;
import ru.raccoon.moneytransferservice.exception.ExceptionData;

import java.util.Calendar;

public class Checker {

    public static void checkTransferParams(String cardFromNumber, String cardToNumber, String cardFromCVV, String cardFromValidTill, int value) {

        //блок проверки правильности указания данных карты (кроме срока действия)

        if (!cardFromNumber.matches("^\\d{16}$")) {
            throw new BadRequestException(new ExceptionData("Номер карты отправителя должен состоять из 16 цифр", 1001));
        }
        if (!cardToNumber.matches("^\\d{16}$")) {
            throw new BadRequestException(new ExceptionData("Номер карты получателя должен состоять из 16 цифр", 1002));
        }
        if (!cardFromCVV.matches("^\\d{3}$")) {
            throw new BadRequestException(new ExceptionData("CVV карты должен состоять из 3 цифр", 1003));
        }
        if (!cardFromValidTill.matches("^\\d{2}(\\/)\\d{2}$")) {
            throw new BadRequestException(new ExceptionData("Срок действия карты должен быть указан в формате MM/YY", 1004));
        }

        //блок проверки правильности указания срока действия карты

        int cardFromMonthValidTill = Integer.parseInt(cardFromValidTill.substring(0, 2));
        int cardFromYearValidTill = Integer.parseInt(cardFromValidTill.substring(3));
        //номер текущего месяца приводим к HumanReadable-формату
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        //делаем допущение, что срок дейсствия карты может распространятся только на 21 век
        int currentYear = Calendar.getInstance().get(Calendar.YEAR) - 2000;

        if ((cardFromMonthValidTill < 1) || (cardFromMonthValidTill > 12)) {
            throw new BadRequestException(new ExceptionData("Месяц должен быть указан числом от 1 до 12", 1005));
        }
        if (cardFromYearValidTill < currentYear) {
            throw new BadRequestException(new ExceptionData("Срок действия карты истёк. Проверьте указанный год", 1006));
        } else if (cardFromYearValidTill == currentYear) {
            if (cardFromMonthValidTill < currentMonth) {
                throw new BadRequestException(new ExceptionData("Срок действия карты истёк. Проверьте указанный месяц", 1007));
            }
        }

        //блок проверки правильности указанной суммы переводы
        if (!(value > 0)) {
            throw new BadRequestException(new ExceptionData("Указана некорретная сумма перевода", 1008));
        }
    }

    public static void checkIdAndCode(String id, String idForCheck, String code, String codeForCheck) {
        if (!id.equals(idForCheck)) {
            throw new BadRequestException(new ExceptionData("Получен неожидаемый идентификатор операции. Перевод не может быть исполнен", 1009));
        }
        if (!code.equals(codeForCheck)) {
            throw new BadRequestException(new ExceptionData("Получен неправильный код подтверждения. Перевод не может быть исполнен", 1010));
        }
    }
}
