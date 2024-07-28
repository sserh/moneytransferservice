package ru.raccoon.moneytransferservice.exception;

import lombok.Getter;

/**
 * Класс исключения BadRequest
 */
@Getter
public class BadRequestException extends RuntimeException {

    private final ExceptionData exceptionData;

    public BadRequestException(ExceptionData exceptionData) {
        this.exceptionData = exceptionData;
    }
}
