package ru.raccoon.moneytransferservice.model;

import lombok.Getter;

@Getter
public class OperationId {
    String operationId;

    public OperationId(String operationId) {
        this.operationId = operationId;
    }
}
