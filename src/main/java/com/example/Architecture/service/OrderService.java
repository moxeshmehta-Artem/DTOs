package com.example.Architecture.service;

import com.example.Architecture.dto.OrderRequestDTO;
import com.example.Architecture.dto.OrderResponseDTO;
import com.example.Architecture.entity.*;
import com.example.Architecture.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {
        @Autowired
        private UserRepository userRepo;
        @Autowired
        private ProductRepository productRepo;
        @Autowired
        private OrderRepository orderRepo;

        public OrderResponseDTO placeOrder(OrderRequestDTO request) {
                // 1. Fetch Entities
                User user = userRepo.findById(request.getUserId())
                                .orElseThrow(() -> new com.example.Architecture.exception.ResourceNotFoundException(
                                                "User not found"));

                Product product = productRepo.findById(request.getProductId())
                                .orElseThrow(
                                                () -> new com.example.Architecture.exception.ResourceNotFoundException(
                                                                "Product not found"));
                // 2. Validate Stock
                if (product.getStock() < request.getQuantity()) {
                        throw new RuntimeException("Insufficient stock");
                }
                // 3. Create Order Entity
                Order order = new Order();
                order.setUser(user);
                order.setProduct(product);
                order.setQuantity(request.getQuantity());
                order.setTotalPrice(product.getPrice() * request.getQuantity());
                order.setStatus("CONFIRMED");
                // 4. Update Stock & Save
                product.setStock(product.getStock() - request.getQuantity());
                productRepo.save(product); // Save stock update
                Order savedOrder = orderRepo.save(order); // Save order
                // 5. Convert to DTO
                return new OrderResponseDTO(
                                savedOrder.getId(),
                                savedOrder.getStatus(),
                                savedOrder.getTotalPrice());
        }

        public java.util.List<OrderResponseDTO> getAllOrders() {
                return orderRepo.findAll().stream()
                                .map(order -> new OrderResponseDTO(
                                                order.getId(),
                                                order.getStatus(),
                                                order.getTotalPrice()))
                                .collect(java.util.stream.Collectors.toList());
        }

        public java.util.List<OrderResponseDTO> getOrdersByUser(String name) {
                return orderRepo.findByUser_Name(name).stream()
                                .map(order -> new OrderResponseDTO(
                                                order.getId(),
                                                order.getStatus(),
                                                order.getTotalPrice()))
                                .collect(java.util.stream.Collectors.toList());
        }

        public Double getTotalRevenue() {
                return orderRepo.calculateTotalRevenue();
        }

        // ------------------- HQL Service Methods -------------------

        public java.util.List<OrderResponseDTO> getOrdersByUserHQL(String name) {
                return orderRepo.findOrdersByUserNameHQL(name).stream()
                                .map(order -> new OrderResponseDTO(
                                                order.getId(),
                                                order.getStatus(),
                                                order.getTotalPrice()))
                                .collect(java.util.stream.Collectors.toList());
        }

        public java.util.List<Object[]> getOrderStatusCounts() {
                return orderRepo.countOrdersByStatus();
        }

        public java.util.List<OrderResponseDTO> getExpensiveOrders(Double minPrice) {
                return orderRepo.findExpensiveOrdersWithProduct(minPrice).stream()
                                .map(order -> new OrderResponseDTO(
                                                order.getId(),
                                                order.getStatus(),
                                                order.getTotalPrice()))
                                .collect(java.util.stream.Collectors.toList());
        }

        public List<OrderResponseDTO> getOrdersByCondition(Double Price) {
                return orderRepo.findOrdersByCondition(Price).stream()
                                .map(order -> new OrderResponseDTO(
                                                order.getId(),
                                                order.getStatus(),
                                                order.getTotalPrice()))
                                .collect(java.util.stream.Collectors.toList());
        }

        // ------------------- EntityGraph Usage -------------------
        public List<OrderResponseDTO> getOrdersByStatus(String status) {
                // This calls the method annotated with @EntityGraph
                List<Order> orders = orderRepo.findByStatus(status);

                // Because "user" and "product" were fetched eagerly,
                // calling getProduct() or getUser() here will NOT trigger new SQL queries.
                return orders.stream()
                                .map(order -> {
                                        // Accessing related entities safely
                                        String productName = order.getProduct().getName();
                                        String userName = order.getUser().getName();

                                        // For this example, we just return the standard DTO,
                                        // but in a real app, you might map these names to a detailed DTO.
                                        return new OrderResponseDTO(
                                                        order.getId(),
                                                        order.getStatus(),
                                                        order.getTotalPrice());
                                })
                                .collect(java.util.stream.Collectors.toList());
        }
}