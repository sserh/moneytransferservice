package ru.raccoon.moneytransferservice.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.raccoon.moneytransferservice.exception.BadRequestException;
import ru.raccoon.moneytransferservice.utils.UtilClass;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Класс логгера
 */
public class EventLogger {

    public static final NumberFormat formatter = new DecimalFormat("#0.00");

    public static final Logger logger = LogManager.getLogger("my-logger-name");

    /** Метод, логгирующий данные об исключении BadRequest
     */
    public static void logBadRequestException(BadRequestException e,
                                              String cardFromNumber,
                                              String cardToNumber,
                                              String cardFromValidTill,
                                              String cardFromCVV) {
        logger.error("\n{}\n{}\n{}\n{}\n{}\n{}\n{}",
                "Карта отправителя: " + UtilClass.addSpaceToCardNumber(cardFromNumber),
                "Карта получателя: " + UtilClass.addSpaceToCardNumber(cardToNumber),
                "Срок действия карты отправителя: " + cardFromValidTill,
                "CVV карты отправителя: " + cardFromCVV,
                "message_id = " + e.getExceptionData().id(),
                "message = " + e.getExceptionData().message(),
                "-------------------------------");
    }

    /** Метод, логгирующий данные об исключении InternalServerError
     */
    public static void logISEException(RuntimeException e) {
        logger.error("\n{}\n{}\n{}",
                e.getMessage(),
                e.getStackTrace(),
                "-------------------------------");
    }

    /** Метод, логгирующий данные об успешном переводе
     */
    public static void lodSuccessTransfer(String cardFromNumber,
                                          String cardToNumber,
                                          String cardFromValidTill,
                                          String cardFromCVV,
                                          int value,
                                          String currency,
                                          double transferSum,
                                          double comSum) {
        logger.info("\nПеревод с карты № {}, срок действия до {}, CVV {}\nна карту № {}\nСумма: {} {}\nПереведено: {} {} Комиссия: {} {}\n-------------------------------",
                UtilClass.addSpaceToCardNumber(cardFromNumber), cardFromValidTill, cardFromCVV, UtilClass.addSpaceToCardNumber(cardToNumber),
                value, currency, formatter.format(transferSum), currency, formatter.format(comSum), currency);
    }
}
