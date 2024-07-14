package ru.raccoon.moneytransferservice.exception;

import lombok.Getter;

@Getter
public class ISEException extends RuntimeException {

    ExceptionData exceptionData;

    public ISEException(ExceptionData exceptionData) {
        this.exceptionData = exceptionData;
    }
}
