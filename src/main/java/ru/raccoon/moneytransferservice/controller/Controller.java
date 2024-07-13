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

    TransferService transferService;

    public Controller(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<OperationId> getTransferParams(@RequestBody Transfer transfer) {
        return transferService.getTransferResponse(transfer);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationId> confirmOperation(@RequestBody ConfirmationData confirmationData) {
        return transferService.getConfirmationResponse(confirmationData);
    }
}
