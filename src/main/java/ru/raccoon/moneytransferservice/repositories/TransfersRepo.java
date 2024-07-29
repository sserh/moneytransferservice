package ru.raccoon.moneytransferservice.repositories;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.raccoon.moneytransferservice.model.Transfer;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, реализующий хранилище для запрошенных клиентами переводов
 */
@Repository
@NoArgsConstructor
public class TransfersRepo {

    //мапа для хранения данных о переводах
    private static final Map<String, Transfer> transfersMap = new HashMap<>();

    /** Метод для добавления в мапу нового перевода
     * @param operationId Идентификатор перевода
     * @param transfer Набор данных о переводе
     */
    public static void addTransfer(String operationId, Transfer transfer) {
        transfersMap.put(operationId, transfer);
    }

    /** Метод, по идентификатору операции проверяющий наличие в мапе перевода
     * @param operationId Идентификатор перевода
     * @return Признак наличия данных о переводе в мапе
     */
    public static boolean isTransferExist(String operationId) {
        return transfersMap.containsKey(operationId);
    }

    /** Метод для удаления перевода из мапы
     * @param operationId Идентификатор перевода
     */
    public static void removeTransfer(String operationId) {
        transfersMap.remove(operationId);
    }

    /** Метод получения кода СМС-подтверждения, присвоенного запросу о переводе
     * @param operationId Идентификатор перевода
     * @return СМС-код для подтверждения перевода, назначенный сервисом для перевода
     */
    public static String getCode(String operationId) {
        return transfersMap.get(operationId).getCODE();
    }

    /** Метод, по идентификатору операции возвращающий экземпляр перевода
     * @param operationId Идентификатор перевода
     * @return Возвращает данные о переводе
     */
    public static Transfer getTransfer(String operationId) {
        return transfersMap.get(operationId);
    }
}
