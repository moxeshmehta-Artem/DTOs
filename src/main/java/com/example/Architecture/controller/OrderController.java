package com.example.Architecture.controller;

import com.example.Architecture.dto.*;
import com.example.Architecture.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO response = orderService.placeOrder(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public java.util.List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    // JPQL Learning Endpoint
    @GetMapping("/by-user")
    public java.util.List<OrderResponseDTO> getOrdersByUser(@RequestParam String name) {
        return orderService.getOrdersByUser(name);
    }

    // Native SQL Learning Endpoint
    @GetMapping("/revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        Double revenue = orderService.getTotalRevenue();
        return ResponseEntity.ok(revenue != null ? revenue : 0.0);
    }
}