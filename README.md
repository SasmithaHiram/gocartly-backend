# GoCartly E-Commerce Backend

A full-featured e-commerce backend REST API built with Spring Boot 3.4.3, JPA/Hibernate, and MySQL.

## Features

- **Product Management**: Complete CRUD operations for products with categories, search, and filtering
- **Category Management**: Organize products into categories
- **User Management**: Customer and admin user management
- **Shopping Cart**: Add, update, and remove items from cart with real-time calculations
- **Order Management**: Complete order processing with order status tracking
- **Address Management**: Support for shipping and billing addresses
- **Review System**: Product reviews and ratings (entity ready)
- **Exception Handling**: Global exception handler with proper error responses
- **API Documentation**: Swagger/OpenAPI documentation available at `/swagger-ui.html`

## Technology Stack

- **Java 23**
- **Spring Boot 3.4.3**
- **Spring Data JPA**
- **MySQL**
- **Lombok** - Reduces boilerplate code
- **ModelMapper** - Object mapping
- **Springdoc OpenAPI** - API documentation

## Prerequisites

- Java 23 or higher
- Maven 3.9+
- MySQL 8.0+

## Getting Started

### Database Setup

1. Install MySQL if not already installed
2. The application will automatically create the database `ecommerce` on first run
3. Default credentials in `application.yml`:
   - Username: `root`
   - Password: `1234`

### Build and Run

```bash
# Clone the repository
git clone https://github.com/SasmithaHiram/gocartly-backend.git
cd gocartly-backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

## API Documentation

Once the application is running, access the Swagger UI at:
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **API Docs**: http://localhost:8081/api-docs

## API Endpoints

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/active` | Get all active products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/category/{categoryId}` | Get products by category |
| GET | `/api/products/search?keyword={keyword}` | Search products by name |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/categories` | Get all categories |
| GET | `/api/categories/{id}` | Get category by ID |
| POST | `/api/categories` | Create new category |
| PUT | `/api/categories/{id}` | Update category |
| DELETE | `/api/categories/{id}` | Delete category |

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/email/{email}` | Get user by email |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Shopping Cart

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cart/user/{userId}` | Get user's cart |
| POST | `/api/cart/user/{userId}/items` | Add item to cart |
| PUT | `/api/cart/user/{userId}/items/{productId}` | Update item quantity |
| DELETE | `/api/cart/user/{userId}/items/{productId}` | Remove item from cart |
| DELETE | `/api/cart/user/{userId}` | Clear entire cart |

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders/user/{userId}` | Create new order |
| GET | `/api/orders/user/{userId}` | Get user's orders |
| GET | `/api/orders/{orderId}` | Get order by ID |
| GET | `/api/orders` | Get all orders |
| PATCH | `/api/orders/{orderId}/status` | Update order status |

## Example Requests

### Create a Category

```bash
curl -X POST http://localhost:8081/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Electronics",
    "description": "Electronic devices and accessories",
    "imageUrl": "https://example.com/electronics.jpg"
  }'
```

### Create a Product

```bash
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "stockQuantity": 50,
    "brand": "TechBrand",
    "categoryId": 1,
    "imageUrl": "https://example.com/laptop.jpg",
    "active": true
  }'
```

### Create a User

```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "customer@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+1234567890",
    "role": "CUSTOMER"
  }'
```

### Add Item to Cart

```bash
curl -X POST http://localhost:8081/api/cart/user/1/items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
```

### Create an Order

```bash
curl -X POST http://localhost:8081/api/orders/user/1 \
  -H "Content-Type: application/json" \
  -d '{
    "paymentMethod": "CREDIT_CARD",
    "shippingAddress": {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zipCode": "10001",
      "country": "USA"
    },
    "billingAddress": {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zipCode": "10001",
      "country": "USA"
    }
  }'
```

## Database Schema

The application uses JPA/Hibernate to automatically manage the database schema. Key entities include:

- **Category**: Product categories
- **Product**: Product information with category relationship
- **User**: Customer and admin users
- **Cart**: Shopping cart per user
- **CartItem**: Items in a cart
- **Order**: Customer orders
- **OrderItem**: Items in an order
- **Address**: Shipping and billing addresses
- **Review**: Product reviews (ready for implementation)

## Configuration

Key configuration in `src/main/resources/application.yml`:

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce?createDatabaseIfNotExist=true
    username: root
    password: 1234
    
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## Order Status Flow

Orders can have the following statuses:
- `PENDING` - Initial state
- `CONFIRMED` - Order confirmed
- `PROCESSING` - Being prepared
- `SHIPPED` - Shipped to customer
- `DELIVERED` - Successfully delivered
- `CANCELLED` - Order cancelled

## Error Handling

The API uses standard HTTP status codes:
- `200 OK` - Successful request
- `201 CREATED` - Resource created successfully
- `204 NO CONTENT` - Successful deletion
- `400 BAD REQUEST` - Invalid request data
- `404 NOT FOUND` - Resource not found
- `500 INTERNAL SERVER ERROR` - Server error

Error responses include:
```json
{
  "timestamp": "2025-10-29T05:30:00",
  "message": "Error description",
  "status": 404
}
```

## Future Enhancements

- JWT-based authentication and authorization
- Payment gateway integration
- Email notifications
- Product inventory management
- Product image upload
- Review and rating system implementation
- Wishlist functionality
- Coupon and discount system
- Advanced search and filtering
- Admin dashboard

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Contact

Project Link: [https://github.com/SasmithaHiram/gocartly-backend](https://github.com/SasmithaHiram/gocartly-backend)
