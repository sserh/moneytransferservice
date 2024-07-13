package ru.raccoon.moneytransferservice.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    ExceptionData exceptionData;

    public BadRequestException(ExceptionData exceptionData) {
        this.exceptionData = exceptionData;
    }
}
