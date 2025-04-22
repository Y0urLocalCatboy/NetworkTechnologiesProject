package com.example.firstproject.controller.dto.drug;

import jakarta.validation.constraints.Size;

public class CreateDrugDto {
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    private String description;

    private Double price;

    private Integer quantity;

    private String manufacturer;

    public CreateDrugDto(String name, String description, Integer quantity, Double price, String manufacturer) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.manufacturer = manufacturer;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
