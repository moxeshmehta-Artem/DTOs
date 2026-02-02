package com.example.Architecture.dto;
public class OrderResponseDTO {
    private Long orderId;
    private String status;
    private Double totalAmount;
    public OrderResponseDTO(Long orderId, String status, Double totalAmount) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
    }
    // Getters
    public Long getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public Double getTotalAmount() { return totalAmount; }
}