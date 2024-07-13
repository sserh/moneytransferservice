package ru.raccoon.moneytransferservice.model;

import lombok.Getter;

@Getter
public class ConfirmationData {
    String operationId;
    String code;

    public ConfirmationData(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }
}
