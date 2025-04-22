package com.example.firstproject.controller.dto.customer;

import jakarta.validation.constraints.Size;

public class CreateCustomerDto {
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;
    @Size(min = 1, max = 50, message = "Surname must be between 1 and 50 characters")
    private String surname;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
