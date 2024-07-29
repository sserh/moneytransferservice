package ru.raccoon.moneytransferservice.controller;

import org.springframework.http.ResponseEntity;
import ru.raccoon.moneytransferservice.model.ConfirmationData;
import ru.raccoon.moneytransferservice.model.OperationId;
import ru.raccoon.moneytransferservice.model.Transfer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.raccoon.moneytransferservice.service.TransferService;

@RestController
public class Controller {

    private final TransferService transferService;

    public Controller(TransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * Метод подготавливает и отправляет клиенту ответ на основании полученных от клиента данных о переводе
     * @param transfer Данные о переводе от клиента
     * @return Возвращает результат обработки сервисом данных о переводе
     */
    @PostMapping("/transfer")
    public ResponseEntity<OperationId> getTransferParams(@RequestBody Transfer transfer) {
        return transferService.getTransferResponse(transfer);
    }

    /**
     * Метод подготавливает и отправляет клиенту ответ о результате обработки подтверждения перевода
     * @param confirmationData Данные для подтверждения перевода
     * @return Возвращает результат обработки сервисом данных для подтверждения перевода
     */
    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationId> confirmOperation(@RequestBody ConfirmationData confirmationData) {
        return transferService.getConfirmationResponse(confirmationData);
    }
}
