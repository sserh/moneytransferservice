package ru.raccoon.moneytransferservice.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Вспомогательный класс, содержащий несколько методов для работы с данными
 */
public final class UtilClass {

    /**Метод, добавляющий пробелы в номер карты, для его вывода в User-readable части программы
     * @param cardNumber Номер карты в формате "ХХХХХХХХХХХХХХХХ"
     * @return Возвращает номер карты в формате "ХХХХ ХХХХ ХХХХ ХХХХ"
     */
    public static String addSpaceToCardNumber(String cardNumber) {
        StringBuilder builder = new StringBuilder(cardNumber);
        builder.insert(12, " ").insert(8, " ").insert(4, " ");
        return builder.toString();
    }

    /**Метод для генерации идентифкатора операции
     * @return Возвращает идентификатор в строковом формате
     */
    public static String generateOperationId() {

        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
