package com.example.firstproject.service.valueObjects;

public class Price {

    private final Double value;

    public Price(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public static Price create(Double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        var roundedPrice = Math.round(value * 100.0) / 100.0;
        return new Price(roundedPrice);
    }
}
