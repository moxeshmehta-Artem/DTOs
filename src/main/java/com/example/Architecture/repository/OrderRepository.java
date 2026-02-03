package com.example.Architecture.repository;

import com.example.Architecture.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // JPQL Learning: Find orders by User's name
    // Spring Data JPA derives this query automatically: SELECT o FROM Order o WHERE
    // o.user.name = :name
    java.util.List<Order> findByUser_Name(String name);

    // Native SQL Learning: Calculate total revenue
    // This executes raw SQL directly on the database
    @org.springframework.data.jpa.repository.Query(value = "SELECT SUM(total_price) FROM orders", nativeQuery = true)
    Double calculateTotalRevenue();
}