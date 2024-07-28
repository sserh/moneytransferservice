package ru.raccoon.moneytransferservice.model;

/** Класс-упаковка для отправки клиенту данных об идентификаторе запроса на перевод
 * @param operationId
 */
public record OperationId(String operationId) {
}
