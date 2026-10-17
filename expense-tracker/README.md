# Personal Expense Tracker (Spring Boot + Maven)

A RESTful Personal Expense Tracker mini-project built with **Java 17**, **Spring Boot 3**, **Spring Data JPA**, and **Maven**.

## Requirements
- Java 17 or higher
- Apache Maven 3.8+

## Running the Project
1. Open a terminal in the `expense-tracker` root directory.
2. Run the application using Maven:
   ```bash
   mvn clean spring-boot:run
   ```
3. The server will start at `http://localhost:8080`.
4. Access the H2 Database Console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:expensedb`, User: `sa`, Password: empty).

## API Endpoints
- `GET /api/expenses` - List all expenses (optional query params: `category`, `startDate`, `endDate`)
- `GET /api/expenses/summary` - Get total spending and category breakdown for a date range
- `POST /api/expenses` - Create a new expense
- `PUT /api/expenses/{id}` - Update an existing expense
- `DELETE /api/expenses/{id}` - Delete an expense
