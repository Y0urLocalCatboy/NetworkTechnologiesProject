package com.example.firstproject.controller.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateCustomerDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String shopcart;

    public UpdateCustomerDto() {
    }

    public UpdateCustomerDto(String name, String surname, String email, String password, String shopcart) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.shopcart = shopcart;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShopcart() {
        return shopcart;
    }

    public void setShopcart(String shopcart) {
        this.shopcart = shopcart;
    }
}