package ru.raccoon.moneytransferservice.exception;

import lombok.Getter;

@Getter
public class ISEException extends RuntimeException {

    ExceptionData exceptionData;
    //static final Logger logger = LogManager.getLogger("my-logger-name");

    public ISEException(ExceptionData exceptionData) {
        this.exceptionData = exceptionData;
    }
}
