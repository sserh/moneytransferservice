package ru.raccoon.moneytransferservice.checker;

import ru.raccoon.moneytransferservice.exception.BadRequestException;
import ru.raccoon.moneytransferservice.exception.ExceptionData;
import ru.raccoon.moneytransferservice.repositories.TransfersRepo;

import java.util.Calendar;

/**
 * Класс с методами для проверки входных данных от клиента
 */
public class Checker {

    /**
     * Этот метод проверяет, корректные ли данные для осуществления перевода прислал нам клиент
     * @param cardFromNumber Номер карты, с которой осуществляется перевод
     * @param cardToNumber Номер карты, на которую осуществляется перевод
     * @param cardFromCVV Проверочный код карты, с которой осуществляется перевод
     * @param cardFromValidTill Срок действия карты, с которой осуществляется перевод
     * @param value Сумма для перевода.
     */
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
        if (!cardFromValidTill.matches("^\\d{2}(/)\\d{2}$")) {
            throw new BadRequestException(new ExceptionData("Срок действия карты должен быть указан в формате MM/YY", 1004));
        }

        //блок проверки правильности указания срока действия карты

        int cardFromMonthValidTill = Integer.parseInt(cardFromValidTill.substring(0, 2));
        int cardFromYearValidTill = Integer.parseInt(cardFromValidTill.substring(3));
        //номер текущего месяца приводим к HumanReadable-формату
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        //делаем допущение, что срок действия карты может распространяться только на 21 век
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
            throw new BadRequestException(new ExceptionData("Указана некорректная сумма перевода", 1008));
        }
    }

    /**
     * Этот метод проверяет, корректные ли данные прислал нам клиент в подтверждении
     * @param idForCheck Идентификатор операции
     * @param codeForCheck "Код из СМС"
     */
    public static void checkIdAndCode(String idForCheck, String codeForCheck) {
        if (!TransfersRepo.isTransferExist(idForCheck)) {
            throw new BadRequestException(new ExceptionData("Получен неожидаемый идентификатор операции. Перевод не может быть исполнен", 1009));
        } else {
            if (!(TransfersRepo.getCode(idForCheck)).equals(codeForCheck)) {
                throw new BadRequestException(new ExceptionData("Получен неправильный код подтверждения. Перевод не может быть исполнен", 1010));
            }
        }
    }
}
