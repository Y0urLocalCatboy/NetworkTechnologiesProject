package com.example.firstproject.service.valueObjects;

public class Name {
    private final String value;

    public String getValue() {
        return value;
    }

    public static Name create(String value) {
        return new Name(value);
    }
    
    public Name(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.value = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }
}
