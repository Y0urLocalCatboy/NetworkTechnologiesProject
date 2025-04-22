package com.example.firstproject.controller.dto.customer;

import com.example.firstproject.service.valueObjects.Name;
import com.example.firstproject.service.valueObjects.Price;

public class CreateCustomerResponseDto {
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    public CreateCustomerResponseDto(Long id, String name, String surname, String email, String password) {
        this.id = id;
        this.name = Name.create(name).getValue();
        this.surname = Name.create(surname).getValue();
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
