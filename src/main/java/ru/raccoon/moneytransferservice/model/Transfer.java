package ru.raccoon.moneytransferservice.model;

public record Transfer(String cardFromNumber, String cardToNumber, String cardFromValidTill, String cardFromCVV,
                       Amount amount) {
}
