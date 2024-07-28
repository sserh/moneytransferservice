package ru.raccoon.moneytransferservice.model;

/** Класс-упаковка для данных о сумме и валюте перевода
 * @param value
 * @param currency
 */
public record Amount(Integer value, String currency) {
}
