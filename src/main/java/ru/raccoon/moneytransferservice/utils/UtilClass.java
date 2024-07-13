package ru.raccoon.moneytransferservice.utils;

import org.apache.commons.lang3.RandomStringUtils;

public final class UtilClass {

    public static String addSpaceToCardNumber(String cardNumber) {
        StringBuilder builder = new StringBuilder(cardNumber);
        builder.insert(12, " ").insert(8, " ").insert(4, " ");
        return builder.toString();
    }

    public static String generateOperationId() {

        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
