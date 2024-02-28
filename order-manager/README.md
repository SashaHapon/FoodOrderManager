# Order Manager

## Project Overview
Order Manager is a web application designed to manage the food ordering process. The project provides a user-friendly platform for users, allowing them to view available dishes, place orders, and track their order history.


## Technology Stack Used

**Backend technologies (Java/Spring):**
- **Spring Boot:** A framework for quickly creating applications in Java.
- **Spring Data JPA:** Makes it easy to interact with your database using the Java Persistence API.
- **Spring Security:** Provides application security, including authentication and authorization.

**Database:**
- **MySQL:** Relational database for storing order and user information.

**Tools and Libraries:**
- **ModelMapper:** For simple mapping between objects.
- **Apache Commons Lang:** A library with utilities to extend the functionality of the standard Java libraries.
- **JSON Web Token (JWT):** For securely transferring information between parties.
- **Liquibase:** For managing and version-controlling database schema changes.
- **Lombok:** Simplifies the creation of Java classes by reducing boilerplate code.
- **Docker containers** Lightweight, portable, and self-sufficient containers that package software with all necessary dependencies. Used to deploy application and database containers.
- **TestContainers:** A Java library that supports writing tests involving Docker containers. It allows for easy setup, execution, and cleanup of containers within test scenarios. TestContainers is particularly useful for integration testing, especially when working with databases or other external services.

**Infrastructure:**
- **Spring Boot Maven Plugin:** To simplify building and running the application.

## Project Requirements

**Functional requirements:**
1. Users can view the menu and place orders.
2. Restaurateurs can manage menus and process orders.
3. The system must support user authentication and secure data storage.

**Non-functional Requirements:**
1. **Performance:** System response time should not exceed established limits.
2. **Security:** User data must be stored and transmitted in encrypted form.
3. **Scalability:** The system must be scalable to handle the growth in the number of users.

## Running Tests with TestContainers
In this project, you can run tests for different databases using Spring test profiles.
To achieve this, follow these steps:

- For MySQL:

  ```bash
  ./mvnw test -P mysql
  ```

- For PostgreSQL:

  ```bash
  ./mvnw test -P postgres
  ```

This will run your tests using the specified database profile, allowing you to test your application against different database systems.

## Configuration Settings

Before running the Food Order Manager project, you may need to customize certain configuration settings to match your local environment.

Below are the key settings that you may need to override in the `application.yml` file:

**Database Configuration:**

- `spring.datasource.url`: JDBC URL for the database.
- `spring.datasource.username`: Username for connecting to the database.
- `spring.datasource.password`: Password for connecting to the database.

Example:
```yaml
spring:
  datasource:
     url: jdbc:mysql://localhost:3306/your_database
     username: your_username
     password: your_password
```
