# API End-to-End Trace: Create Order

## Flow Diagram

```mermaid
sequenceDiagram
    participant Client
    participant OrderController
    participant OrderService
    participant UserRepository
    participant ProductRepository
    participant OrderRepository
    participant Database

    Client->>OrderController: POST /api/orders (JSON)
    Note right of Client: {userId: 1, productId: 1, quantity: 1}
    
    OrderController->>OrderService: placeOrder(OrderRequestDTO)
    
    activate OrderService
    OrderService->>UserRepository: findById(1)
    UserRepository->>Database: SELECT * FROM users WHERE id=1
    Database-->>UserRepository: User Entity (John Doe)
    UserRepository-->>OrderService: User Entity
    
    OrderService->>ProductRepository: findById(1)
    ProductRepository->>Database: SELECT * FROM products WHERE id=1
    Database-->>ProductRepository: Product Entity (Laptop)
    ProductRepository-->>OrderService: Product Entity
    
    OrderService->>OrderService: Validate Stock (10 > 1)
    
    OrderService->>OrderService: Create Order Entity
    Note right of OrderService: status="CONFIRMED", total=1000
    
    OrderService->>ProductRepository: save(Product) (Stock -1)
    ProductRepository->>Database: UPDATE products SET stock=9...
    
    OrderService->>OrderRepository: save(Order)
    OrderRepository->>Database: INSERT INTO orders...
    Database-->>OrderRepository: Saved Order Entity
    OrderRepository-->>OrderService: Saved Order Entity
    
    OrderService-->>OrderController: OrderResponseDTO
    deactivate OrderService
    
    OrderController-->>Client: 201 Created (JSON)
    Note right of Client: {orderId: 2, status: "CONFIRMED"...}
```

## Step-by-Step Trace

1.  **Request**: The client sends a POST request with `userId: 1` and `productId: 1`.
2.  **Controller**: `OrderController` receives the JSON and converts it to `OrderRequestDTO`. It calls `orderService.placeOrder()`.
3.  **Service - Fetch Data**: `OrderService` asks `UserRepository` and `ProductRepository` for the entities.
4.  **Database Query**: Hibernate executes `SELECT` queries to get `User` and `Product` data.
5.  **Logic**: The Service checks if `Product.stock` is sufficient.
6.  **State Change**: A new `Order` object is created. The `Product` stock is decremented in memory.
7.  **Persist**: The Service calls `save()` on repositories. Hibernate executes `UPDATE` for the product and `INSERT` for the order.
8.  **Response**: The Service converts the saved Order entity into an `OrderResponseDTO` and returns it.
9.  **Reply**: The Controller wraps this in a `ResponseEntity` with HTTP 201 status and sends it back to the client.
