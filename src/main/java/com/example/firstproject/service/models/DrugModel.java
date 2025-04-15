package com.example.firstproject.service.models;

import com.example.firstproject.service.valueObjects.Price;

public class DrugModel {

    /**
     * if that would be a DrugModel there would be an ID
     */
    private Long id;

    private String name;

    private String description;

    private Double price;

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
        this.price = Price.create(price).getValue();
    }

    public DrugModel(Long id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
