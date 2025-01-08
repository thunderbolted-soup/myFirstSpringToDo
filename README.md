# My Simple Todo App

A basic todo application built with Spring Boot that demonstrates basic CRUD operations and user management.

## Technologies Used
- Java 23
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- Thymeleaf (not used rn)
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

### REST API things

#### Users
- GET `/api/v1/users` - Get all users
- GET `/api/v1/users/{id}` - Get user by ID
- GET `/api/v1/users/search` - Search users by username or email
- POST `/api/v1/users` - Create new user
- PUT `/api/v1/users/{id}` - Update existing user
- DELETE `/api/v1/users/{id}` - Delete user

#### Tasks
- GET `/api/v1/users/{userId}/tasks` - Get all tasks for user
- GET `/api/v1/users/{userId}/tasks/{id}` - Get task by ID
- GET `/api/v1/users/{userId}/tasks/search` - Search tasks by title
- POST `/api/v1/users/{userId}/tasks` - Create new task
- PUT `/api/v1/users/{userId}/tasks/{id}` - Update existing task
- DELETE `/api/v1/users/{userId}/tasks/{id}` - Delete task



# Project Structure

```
src/main/java/kg/baybackage/mysimpletodoapp/
├── controllers/
│   └── UserController.java
│   └── TaskController.java
├── models/
│   └── Task.java
│   └── User.java
├── repository/
│   └── TaskRepository.java
│   └── UserRepository.java
└── services/
    └── UserService.java
    └── TaskService.java
```

## Development

This is a learning project that implements basic Spring Boot features. Future improvements might include:
- User authentication
- Frontend improvements

## Version
Current version: 0.2

## Author
Anvar Keldibekov aka thunderbolted-soup
