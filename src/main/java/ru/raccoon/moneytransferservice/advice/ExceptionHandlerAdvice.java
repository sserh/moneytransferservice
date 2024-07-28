package ru.raccoon.moneytransferservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.raccoon.moneytransferservice.exception.ExceptionData;
import ru.raccoon.moneytransferservice.exception.BadRequestException;
import ru.raccoon.moneytransferservice.exception.ISEException;

/**
 * Класс, регулирующий отправку клиенту ошибок, связанных с исключениями BadRequest и InternalServerError
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionData> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new ExceptionData(e.getExceptionData().message(), e.getExceptionData().id()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionData> handleISRException(ISEException e) {
        return new ResponseEntity<>(new ExceptionData(e.getExceptionData().message(), e.getExceptionData().id()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
