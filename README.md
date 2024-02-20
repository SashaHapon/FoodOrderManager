# Food Order Manager

## Business Purpose of the Project:

Food Order Manager is a web application designed to manage the food ordering process. The project provides a user-friendly platform for users, allowing them to view available dishes, place orders, and track their order history.

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

## Actions to Launch the Project

### Prerequisites:
- JDK 21 or higher
- Docker

### Steps

1. Cloning the Repository:

```bash
git clone https://github.com/SashaHapon/FoodOrderManager.git
````

2. Go to the Project Directory.

```bash
cd FoodOrderManager
```

3. Selecting a database for the application. This application supports using different databases using Spring profiles. Below are instructions on how to run the application with each of the supported databases.
4. Running the application

   - with MySQL:

        ```bash
        docker compose --file docker-compose-mysql.yml up --detach
        ```

   - with PostgreSQL:

      ```bash
      docker compose --file docker-compose-postgres.yml up --detach
      ```
5. Access to the Application:
   
   After successful launch, the application will be available at: http://localhost:8080

6. Functionality Check:
   
   Open your web browser and go to the provided address.

   Log in (if necessary) and start using Food Order Manager.

## Running Tests with TestContainers
In this project, you can run tests for different databases using Spring test profiles.
To achieve this, follow these steps:

**For MySQL:**
```bash
./mvnw test -D spring.profiles.active=testcontainers,mysqltest
```
**For PostgreSQL:**

```bash
./mvnw test -D spring.profiles.active=testcontainers,postgrestest
```
This will run your tests using the specified database profile, allowing you to test your application against different database systems.



## Configuration Settings

Before running the Food Order Manager project, you may need to customize certain configuration settings to match your local environment. Below are the key settings that you may need to override in the `application.yml` file:

**Database Configuration:**
   - `spring.datasource.url`: JDBC URL for the MySQL database.
   - `spring.datasource.username`: Username for connecting to the MySQL database.
   - `spring.datasource.password`: Password for connecting to the MySQL database.

Example:
```yaml
spring:
  datasource:
     url: jdbc:mysql://localhost:3306/your_database
     username: your_username
     password: your_password
```