# Food Order Manager

## Business Purpose of the Project:

Food Order Manager is a web application designed to manage the food ordering process. The project provides a user-friendly platform for users, allowing them to view available dishes, place orders, and track their order history. And printing receipts.

## Services
The project consists of the following services:
- [Order manager](order-manager/README.md): Designed to manage the food ordering process.
- [Reciept](receipt-service/README.md): Designed to manage the receipt printing process.

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
- JDK 17 or higher
- Docker

### Steps

1. Clone the repository

   ```bash
   git clone https://github.com/SashaHapon/FoodOrderManager.git
   ````

2. Navigate to the Project Directory

   ```bash
   cd FoodOrderManager
   ```

3. Running the application

   - with MySQL:

     ```bash
     docker compose --file docker-compose-mysql.yml up --detach
     ```

   - with PostgreSQL:

     ```bash
     docker compose --file docker-compose-postgres.yml up --detach
     ```

4. Access to the org.order-manager.Application:
   
   After successful launch, the application will be available at: http://localhost:8080

5. Functionality Check:
   
   Open your web browser and go to the provided address.

   Log in (if necessary) and start using Food Order Manager.
