package ru.raccoon.moneytransferservice.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.raccoon.moneytransferservice.checker.Checker;
import ru.raccoon.moneytransferservice.exception.BadRequestException;
import ru.raccoon.moneytransferservice.exception.ExceptionData;
import ru.raccoon.moneytransferservice.exception.ISEException;
import ru.raccoon.moneytransferservice.logger.EventLogger;
import ru.raccoon.moneytransferservice.model.ConfirmationData;
import ru.raccoon.moneytransferservice.model.OperationId;
import ru.raccoon.moneytransferservice.model.Transfer;
import ru.raccoon.moneytransferservice.utils.UtilClass;

@Service
public class TransferService {

    String cardFromNumber;
    String cardToNumber;
    String cardFromValidTill;
    String cardFromCVV;
    int value;
    String currency;
    double transferSum;
    double comSum;
    String operationId;
    String code = "0000"; //front всегда отправляет такой код подтверждения
    final String ERROR_500 = "Внутренняя ошибка сервера. Мы уже работаем над её устранением";


    public ResponseEntity<OperationId> getTransferResponse(Transfer transfer) {

        this.cardFromNumber = transfer.getCardFromNumber();
        this.cardToNumber = transfer.getCardToNumber();
        this.cardFromValidTill = transfer.getCardFromValidTill();
        this.cardFromCVV = transfer.getCardFromCVV();
        this.value = transfer.getAmount().getValue()/100;
        this.currency = transfer.getAmount().getCurrency();
        this.transferSum = value*99d/100;
        this.comSum = value - transferSum;
        this.operationId = UtilClass.generateOperationId();

        //осуществляем проверку полученных данных
        try {
            Checker.checkTransferParams(cardFromNumber, cardToNumber, cardFromCVV, cardFromValidTill, value);
        } catch (BadRequestException e) {
            EventLogger.logBadRequestException(e, cardFromNumber, cardToNumber,cardFromCVV, cardFromValidTill);
            throw new BadRequestException(e.getExceptionData());
        } catch (RuntimeException e) {
            EventLogger.logISEException(e);
            throw new ISEException(new ExceptionData(ERROR_500, 1011));
        }

        return new ResponseEntity<>(new OperationId(operationId), HttpStatus.OK);
    }

    public ResponseEntity<OperationId> getConfirmationResponse(ConfirmationData confirmationData) {

        //проверяем совпадение Id операции и кода подтверждения
        try {
            Checker.checkIdAndCode(operationId, confirmationData.getOperationId(), code, confirmationData.getCode());
        } catch (BadRequestException e) {
            EventLogger.logBadRequestException(e, cardFromNumber, cardToNumber,cardFromCVV, cardFromValidTill);
            throw new BadRequestException(e.getExceptionData());
        } catch (RuntimeException e) {
            EventLogger.logISEException(e);
            throw new ISEException(new ExceptionData(ERROR_500, 1012));
        }
        EventLogger.lodSuccessTransfer(cardFromNumber, cardToNumber, cardFromValidTill, cardFromCVV, value, currency, transferSum, comSum);
        return new ResponseEntity<>(new OperationId(operationId), HttpStatus.OK);
    }
}
