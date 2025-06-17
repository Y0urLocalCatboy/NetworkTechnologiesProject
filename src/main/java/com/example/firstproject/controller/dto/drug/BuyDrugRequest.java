package com.example.firstproject.controller.dto.drug;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BuyDrugRequest {
    @NotNull
    private Long drugId;

    @NotNull
    @Min(0)
    private Integer quantity;

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}