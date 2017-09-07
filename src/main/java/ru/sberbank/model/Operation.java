package ru.sberbank.model;

public enum Operation {
    SALE, BUY;

    static Operation parse(String value) throws IllegalArgumentException {
        if(value == null)
            throw new IllegalArgumentException("Wrong value: " + value);

        switch (value) {
            case "b":
                return BUY;
            case "s":
                return SALE;
            default:
                throw new IllegalArgumentException("Wrong value: " + value);
        }
    }
};

