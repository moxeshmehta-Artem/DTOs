package com.example.Architecture.dto;
public class OrderRequestDTO {
    private Long userId;
    private Long productId;
    private Integer quantity;
    // Getters and Setters are required for JSON parsing
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}