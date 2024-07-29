package ru.raccoon.moneytransferservice.service;


import lombok.Data;
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
import ru.raccoon.moneytransferservice.repositories.TransfersRepo;

/**
 * Класс, реализующий сервис, обрабатывающий детали пользовательских данных о переводе и о подтверждении перевода
 */
@Service
@Data
public class TransferService {

    private final String ERROR_500 = "Внутренняя ошибка сервера. Мы уже работаем над её устранением";

    /** Метод, обрабатывающий данные о переводе, полученные от пользователя
     * @param transfer Данные о переводе, полученные от пользователя
     * @return Возвращает идентификатор операции либо исключения BadRequest или InternalServerError
     */
    public ResponseEntity<OperationId> getTransferResponse(Transfer transfer) {

        try {
            Checker.checkTransferParams(transfer.getCardFromNumber(), transfer.getCardToNumber(), transfer.getCardFromCVV(), transfer.getCardFromValidTill(), transfer.getValue());
        } catch (BadRequestException e) {
            //если данные некорректные, то пишем всё что надо в лог
            EventLogger.logBadRequestException(e, transfer.getCardFromNumber(), transfer.getCardToNumber(), transfer.getCardFromCVV(), transfer.getCardFromValidTill());
            //и выкидываем исключение
            throw new BadRequestException(e.getExceptionData());
        } catch (RuntimeException e) {
            //если проблема есть, и она не в неверных данных, то это ошибка сервера.
            //пишем в лог конкретную ошибку
            EventLogger.logISEException(e);
            //но пользователю необязательно знать, что конкретно мы плохо накодили, отправим только то, что "мы работаем над этим"
            throw new ISEException(new ExceptionData(ERROR_500, 1011));
        }

        //если все данные верные, то добавим транзакцию в репозиторий и будем ждать confirmation
        TransfersRepo.addTransfer(transfer.getOperationId(), transfer);

        return new ResponseEntity<>(new OperationId(transfer.getOperationId()), HttpStatus.OK);
    }

    /** Метод, обрабатывающий параметры подтверждения перевода, полученные от пользователя
     * @param confirmationData Данные для подтверждения перевода, полученные от пользователя
     * @return Возвращает идентификатор успешно проведённого перевода либо исключения BadRequest или InternalServerError
     */
    public ResponseEntity<OperationId> getConfirmationResponse(ConfirmationData confirmationData) {

        //Найдём в репозитории данные о трансфере
        Transfer transfer = TransfersRepo.getTransfer(confirmationData.operationId());

        //проверяем совпадение Id операции и кода подтверждения
        try {
            Checker.checkIdAndCode(confirmationData.operationId(), confirmationData.code());
        } catch (BadRequestException e) {
            //если идентифакатор операции или "код из смс" некорректные, то выкидываем соответствующие исключения
            EventLogger.logBadRequestException(e, transfer.getCardFromNumber(), transfer.getCardToNumber(), transfer.getCardFromCVV(), transfer.getCardFromValidTill());
            throw new BadRequestException(e.getExceptionData());
        } catch (RuntimeException e) {
            EventLogger.logISEException(e);
            throw new ISEException(new ExceptionData(ERROR_500, 1012));
        }
        //если всё хорошо, то пишем данные по операции в лог
        EventLogger.lodSuccessTransfer(transfer.getCardFromNumber(), transfer.getCardToNumber(), transfer.getCardFromValidTill(), transfer.getCardFromCVV(),
                transfer.getValue(), transfer.getCurrency(), transfer.getTransferSum(), transfer.getComSum());
        //удаялем из репозитория ненужные больше данные о запросе на трансфер
        TransfersRepo.removeTransfer(confirmationData.operationId());
        return new ResponseEntity<>(new OperationId(confirmationData.operationId()), HttpStatus.OK);
    }
}
