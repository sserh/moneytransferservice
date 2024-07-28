package ru.raccoon.moneytransferservice.model;

import lombok.Getter;
import lombok.Setter;
import ru.raccoon.moneytransferservice.utils.UtilClass;

/**
 * Класс-упаковка с данными о деталях запрошенного клиентом перевода
 */
@Getter
@Setter
public final class Transfer {

    private final String cardFromNumber;
    private final String cardToNumber;
    private final String cardFromValidTill;
    private final String cardFromCVV;
    private final Amount amount;

    private final int value;
    private final String currency;
    private final double transferSum;
    private final double comSum;

    private final String operationId = UtilClass.generateOperationId();
    private final String CODE = "0000";

    public Transfer(String cardFromNumber, String cardToNumber, String cardFromValidTill, String cardFromCVV, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.amount = amount;

        value = amount.value()/100;
        currency = amount.currency();
        transferSum = value*99d/100;
        comSum = value - transferSum;
    }
}
