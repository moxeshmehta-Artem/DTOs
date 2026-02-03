# API Flow Tutorial: Visualized

This guide visualizes the lifecycle of your requests using Sequence Diagrams.

---

## 1. Create User Flow
**Scenario**: You send `{"name": "Moxesh"}` to create a user.

```mermaid
sequenceDiagram
    participant C as Client (You)
    participant CTL as UserController
    participant SVC as UserService
    participant REP as UserRepository
    participant DB as Database (MySQL)

    C->>CTL: POST /api/users <br/> {name: "Moxesh"}
    CTL->>SVC: createUser(DTO)
    SVC->>SVC: Convert DTO to Entity
    SVC->>REP: save(User)
    REP->>DB: INSERT INTO users VALUES ("Moxesh")
    DB-->>REP: Returns ID (3)
    REP-->>SVC: Returns User Entity (id=3)
    SVC-->>CTL: Returns UserResponseDTO
    CTL-->>C: 201 Created <br/> {id: 3, name: "Moxesh"}
```

---

## 2. Place Order Flow (Complex Logic)
**Scenario**: Moxesh (ID 3) buys 1 Laptop (ID 1).

```mermaid
sequenceDiagram
    participant C as Client
    participant CTL as OrderController
    participant SVC as OrderService
    participant REP as Repositories
    participant DB as DB

    C->>CTL: POST /api/orders <br/> {userId: 3, productId: 1}
    CTL->>SVC: placeOrder(DTO)
    
    note right of SVC: Validation Phase
    SVC->>REP: findUser(3)
    REP->>DB: SELECT * FROM users WHERE id=3
    DB-->>SVC: User Found
    
    SVC->>REP: findProduct(1)
    REP->>DB: SELECT * FROM products WHERE id=1
    DB-->>SVC: Product Found (Stock: 7, Price: 1000)
    
    SVC->>SVC: Check Stock (7 > 1? Yes)
    SVC->>SVC: Calculate Total (1000 * 1)
    
    note right of SVC: Persistence Phase
    SVC->>REP: save(Order)
    REP->>DB: INSERT INTO orders ...
    REP->>DB: UPDATE products SET stock=6 ...
    
    SVC-->>CTL: Returns OrderResponseDTO
    CTL-->>C: 201 Created <br/> {orderId: 1, total: 1000}
```

---

## 3. "Find Orders by User" (JPQL Flow)
**Scenario**: Get all orders for User "Moxesh".

```mermaid
sequenceDiagram
    participant C as Client
    participant CTL as OrderController
    participant SVC as OrderService
    participant REP as OrderRepository
    participant DB as DB

    C->>CTL: GET /orders/by-user?name=Moxesh
    CTL->>SVC: getOrdersByUser("Moxesh")
    SVC->>REP: findByUser_Name("Moxesh")
    
    note right of REP: Spring Magic (JPQL)
    REP->>DB: SELECT * FROM orders o <br/> JOIN users u ON o.user_id = u.id <br/> WHERE u.name = 'Moxesh'
    
    DB-->>REP: [Order Rows]
    REP-->>SVC: List<Order> Entities
    SVC->>SVC: Convert to DTOs
    SVC-->>CTL: List<OrderResponseDTO>
    CTL-->>C: 200 OK <br/> JSON Array
```

---

## 4. "Calculate Revenue" (Native SQL Flow)
**Scenario**: Get total earnings.

```mermaid
sequenceDiagram
    participant C as Client
    participant CTL as OrderController
    participant SVC as OrderService
    participant REP as OrderRepository
    participant DB as DB

    C->>CTL: GET /orders/revenue
    CTL->>SVC: getTotalRevenue()
    SVC->>REP: calculateTotalRevenue()
    
    note right of REP: Native SQL (Direct)
    REP->>DB: SELECT SUM(total_price) FROM orders
    
    DB-->>REP: 1000.0
    REP-->>SVC: Double
    SVC-->>CTL: Double
    CTL-->>C: 200 OK <br/> 1000.0
```
