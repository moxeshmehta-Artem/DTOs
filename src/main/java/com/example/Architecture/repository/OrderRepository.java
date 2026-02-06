package com.example.Architecture.repository;

import com.example.Architecture.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.Architecture.projection.OrderProjection;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // JPQL : Find orders by User's name
    // Spring Data Projection: Returns a lightweight Proxy (Interface) instead of
    // the full Entity.
    List<OrderProjection> findByUser_Name(String name);

    // Native SQL Learning: Calculate total revenue
    @Query(value = "SELECT SUM(total_price) FROM orders", nativeQuery = true)
    Double calculateTotalRevenue();

    // 1. Explicit Simple HQL with Projection
    // Logic: Select specific fields and construct the DTO immediately.
    // This reduces data transfer by not fetching the entire entity.
    @Query("SELECT new com.example.Architecture.dto.OrderResponseDTO(o.id, o.status, o.totalPrice) FROM Order o WHERE o.user.name = :name")
    List<com.example.Architecture.dto.OrderResponseDTO> findOrdersByUserNameHQL(String name);

    // 2. HQL Aggregation (GROUP BY)
    // Logic: Group orders by their status and count how many orders are in each
    // status.f
    // Returns a list of arrays where Object[0] is status (String) and Object[1] is
    // count (Long).
    @Query("SELECT o.status, COUNT(o) FROM Order o GROUP BY o.status")
    List<Object[]> countOrdersByStatus();

    // 3. HQL with JOIN FETCH (Optimization Logic)
    // Logic: Find orders with total price > minPrice.
    // 'JOIN FETCH' eagerly loads the 'product' association to avoid N+1 select
    // problems
    // when accessing the product details later.
  

    @Query("SELECT o FROM Order o WHERE o.totalPrice > :price")
    List<Order> findOrdersByCondition(Double price);

}