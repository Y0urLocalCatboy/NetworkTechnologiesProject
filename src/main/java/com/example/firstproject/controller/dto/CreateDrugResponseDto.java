package com.example.firstproject.controller.dto;

import com.example.firstproject.service.valueObjects.Name;
import com.example.firstproject.service.valueObjects.Price;

public class CreateDrugResponseDto {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer quantity;

    private String manufacturer;

    public CreateDrugResponseDto(Long id, String name, String description, Double price, Integer quantity, String manufacturer) {
        this.id = id;
        this.name = Name.create(name).getValue();
        this.description = description;
        this.price = Price.create(price).getValue();
        this.quantity = quantity;
        this.manufacturer = Name.create(manufacturer).getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
