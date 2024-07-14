package ru.raccoon.moneytransferservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.raccoon.moneytransferservice.exception.BadRequestException;
import ru.raccoon.moneytransferservice.exception.ExceptionData;
import ru.raccoon.moneytransferservice.exception.ISEException;
import ru.raccoon.moneytransferservice.model.Amount;
import ru.raccoon.moneytransferservice.model.ConfirmationData;
import ru.raccoon.moneytransferservice.model.OperationId;
import ru.raccoon.moneytransferservice.model.Transfer;
import ru.raccoon.moneytransferservice.service.TransferService;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    TransferService transferService = Mockito.mock(TransferService.class);
    Controller controller = new Controller(transferService);

    Transfer BadCardFromNumberTransfer = new Transfer("11111111111111111",
            "2222222222222222",
            "05/25",
            "232",
            new Amount(1000, "RUR"));

    Transfer GoodCardFromNumberTransfer = new Transfer("1111111111111111",
            "2222222222222222",
            "05/25",
            "232",
            new Amount(1000, "RUR"));

    ConfirmationData badCConfirmationData = new ConfirmationData("100501", "101");

    ConfirmationData goodConfirmationData = new ConfirmationData("100500", "100");


    @Test
    void getTransferParams() {

        //проверим, что при невалидных метод выбросит исключение, полученное от сервиса
        Mockito.when(transferService.getTransferResponse(BadCardFromNumberTransfer)).thenThrow(new BadRequestException(new ExceptionData("CardFromNumber is invalid", 1001)));
        Assertions.assertThrows(BadRequestException.class, () -> controller.getTransferParams(BadCardFromNumberTransfer));
        //проверим, что при валидных данных исключения не будет
        Mockito.when(transferService.getTransferResponse(GoodCardFromNumberTransfer)).thenReturn(new ResponseEntity<>(new OperationId("100500"), HttpStatus.OK));
        Assertions.assertDoesNotThrow(() -> controller.getTransferParams(GoodCardFromNumberTransfer));
        //и метод вернёт ожидаемый экземпляр класса, полученный от сервиса
        Assertions.assertInstanceOf(ResponseEntity.class, controller.getTransferParams(GoodCardFromNumberTransfer));
    }

    @Test
    void confirmOperation() {
        //проверим, что при правильном id и коде подтверждения мы ответим клиенту корректно
        Mockito.when(transferService.getConfirmationResponse(goodConfirmationData)).thenReturn(new ResponseEntity<>(new OperationId("100500"), HttpStatus.OK));
        Assertions.assertDoesNotThrow(() -> controller.confirmOperation(goodConfirmationData));
        //при некорректном коде подтверждения мы ответим ошибкой
        Mockito.when(transferService.getConfirmationResponse(badCConfirmationData)).thenThrow(new BadRequestException(new ExceptionData("Code is incorrect", 1002)));
        Assertions.assertThrows(BadRequestException.class, () -> controller.confirmOperation(badCConfirmationData));
    }

}