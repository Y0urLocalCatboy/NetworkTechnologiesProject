package com.example.firstproject.controller.dto.customer;

public class GetCustomerDto {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String shopcart;

    public String getShopcart() {
        return shopcart;
    }

    public void setShopcart(String shopcart) {
        this.shopcart = shopcart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public GetCustomerDto(long id, String name, String surname, String email, String shopcart) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.shopcart = shopcart;
    }
}
