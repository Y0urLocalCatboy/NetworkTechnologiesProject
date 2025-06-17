package com.example.firstproject.controller.dto.drug;

public class BuyDrugResponse {
    private Long orderId;
    private String message;
    private boolean success;

    public BuyDrugResponse() {}

    public BuyDrugResponse(Long orderId, String message, boolean success) {
        this.orderId = orderId;
        this.message = message;
        this.success = success;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}