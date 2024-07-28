package ru.raccoon.moneytransferservice.model;

/** Класс-упаковка для данных с подтверждением перевода
 * @param operationId
 * @param code
 */
public record ConfirmationData(String operationId, String code) {
}
