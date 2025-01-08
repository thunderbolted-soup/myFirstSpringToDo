# My Simple Todo App

A basic todo application built with Spring Boot that demonstrates basic CRUD operations and user management.

## Technologies Used
- Java 23
- Spring Boot 3.4.1
- Spring Data JPA
- Spring Security
- PostgreSQL
- Thymeleaf
- Lombok
- Maven

## Features
- User management (create, read, update, delete)
- Email and username validation
- Basic error handling
- RESTful API endpoints
- Database persistence
- Form validation
- Simple logging

## Getting Started

### Prerequisites
- Java 23
- Maven
- PostgreSQL

### Installation
1. Clone the repository
2. Configure PostgreSQL database connection in `application.properties`
3. Run the following commands:
```bash
mvn clean install
mvn spring-boot:run
```

### API Endpoints

#### Users
- GET `/api/v1/users` - Get all users
- GET `/api/v1/users/{id}` - Get user by ID
- GET `/api/v1/users/search` - Search users by username or email
- POST `/api/v1/users` - Create new user
- PUT `/api/v1/users/{id}` - Update existing user
- DELETE `/api/v1/users/{id}` - Delete user

# Project Structure

```
src/main/java/kg/baybackage/mysimpletodoapp/$
├── controllers/$
│   └── UserController.java$
├── models/$
│   └── User.java$
├── repository/$
│   └── UserRepository.java$
└── services/$
    └── UserService.java$
```

## Development

This is a learning project that implements basic Spring Boot features. Future improvements might include:
- Todo items functionality
- User authentication
- Better error handling
- Frontend improvements

## Version
Current version: 0.2

## Author
Baybackage

## Note
This is a simple implementation meant for learning purposes and might need additional security measures before production use.
