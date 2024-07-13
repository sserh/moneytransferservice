package ru.raccoon.moneytransferservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionData {
    String message;
    Integer id;

    public ExceptionData(String message, Integer id) {
        this.message = message;
        this.id = id;
    }
}
