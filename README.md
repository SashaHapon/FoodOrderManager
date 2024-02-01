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

1. **Cloning the Repository:**
   
   git clone https://github.com/SashaHapon/FoodOrderManager.git

2. **Go to the Project Directory:**

3. **Build and Run the application using Maven:**

   ./mvnw spring-boot:run

4. **Access to the Application:**
   After successful launch, the application will be available at: http://localhost:8080

5. **Functionality Check:**
   Open your web browser and go to the provided address.
   Log in (if necessary) and start using Food Order Manager.

 These steps assume that Maven is already installed on your machine. Make sure you also have
 a MySQL database configured, and all project dependencies are satisfied.

## Configuration Settings

Before running the Food Order Manager project, you may need to customize certain configuration settings to match your local environment. Below are the key settings that you may need to override in the `application.properties` file:

**Database Configuration:**
   - `spring.datasource.url`: JDBC URL for the MySQL database.
   - `spring.datasource.username`: Username for connecting to the MySQL database.
   - `spring.datasource.password`: Password for connecting to the MySQL database.

   Example:
      ```application.yml
      spring:
         datasource:
            url: jdbc:mysql://localhost:3306/your_database
            username: your_username
            password: your_password