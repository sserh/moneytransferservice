package ru.raccoon.moneytransferservice.model;

import lombok.Getter;

@Getter
public class Amount {
    private final Integer value;
    private final String currency;

    public Amount(Integer value, String currency) {
        this.value = value;
        this.currency = currency;
    }
}
