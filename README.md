## Order Service Microservice
A Spring Boot microservice responsible for managing order data and processing. This service is part of the Customer Orders microservices system, demonstrating microservices architecture, RESTful API design, and cloud-ready deployment.

---

## 🧩 Features
- CRUD operations for order entities
- RESTful APIs following best practices
- Spring Boot 3 with layered architecture
- PostgreSQL database integration
- Clean, scalable codebase suitable for microservices environments

## 🚀 Tech Stack
- Java 21
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL
- Maven

## ⚙️ Getting Started
Prerequisites
- Java 21
- Maven
- Git

## Clone & Run
```
git clone https://github.com/mohammad-zoubi244/order-service.git
cd order-service
mvn clean install
```
The service will be available at:
http://localhost:8083/order

---

## 🔌 API Endpoints
| Method | Endpoint                             | Description         |
| ------ | ------------------------------------ | ------------------- |
| GET    | /api/v1/orders                       | Retrieve all orders |
| GET    | /api/v1/orders/{orderId}             | Get an order by ID  |
| GET    | /api/v1/orders/customer/{customerId} | Get customer orders |
| GET    | /api/v1/orders/{orderId}/items       | Get an order items     |
| POST   | /api/v1/orders                       | Create a new order  |
| PUT    | /api/v1/orders/{orderId}             | Update an order info   |
| PATCH  | /api/v1/orders/{orderId}/status      | Update an order status |
| DELETE | /api/v1/orders/{orderId}             | Delete an order     |

---

## 📌 API Documentation
Once the service is running locally, you can access Swagger UI here:

Order Service Swagger UI: http://localhost:8083/order/swagger-ui/index.html

---

## 📄 License
This project is licensed under the MIT License. You are free to use, modify, and distribute this software with proper attribution. See the LICENSE file for more details.

---

## 👤 Author
Mohammad Zoubi
