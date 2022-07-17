package com;

public enum MoneyTemplate {
    ONE(1),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    ONE_HUNDRED(100);

    private int value;

    MoneyTemplate(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
