package ru.raccoon.moneytransferservice.model;

import lombok.Getter;

@Getter
public class Transfer {
    private final String cardFromNumber;
    private final String cardToNumber;
    private final String cardFromValidTill;
    private final String cardFromCVV;
    private final Amount amount;

    public Transfer(String cardFromNumber, String cardToNumber, String cardFromValidTill, String cardFromCVV, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.amount = amount;
    }
}
