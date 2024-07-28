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
     * @param operationId
     * @param transfer
     */
    public static void addTransfer(String operationId, Transfer transfer) {
        transfersMap.put(operationId, transfer);
    }

    /** Метод, по идентификатору операции проверяющий наличие в мапе перевода
     * @param operationId
     * @return
     */
    public static boolean isTransferExist(String operationId) {
        return transfersMap.containsKey(operationId);
    }

    /** Метод для удаления перевода из мапы
     * @param operationId
     */
    public static void removeTransfer(String operationId) {
        transfersMap.remove(operationId);
    }

    /** Метод получения кода СМС-подтверждения, присвоенного запросу о переводе
     * @param operationId
     * @return
     */
    public static String getCode(String operationId) {
        return transfersMap.get(operationId).getCODE();
    }

    /** Метод, по идентийикатору операции возвращающий экземпляр перевода
     * @param operationId
     * @return
     */
    public static Transfer getTransfer(String operationId) {
        return transfersMap.get(operationId);
    }
}
