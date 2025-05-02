
# smart-sum-api

A REST API that performs arithmetic calculations with a dynamic percentage, logs each request asynchronously, and uses a modern infrastructure based on Spring Boot and Docker.

---

## 🚀 Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Spring Cache + Redis
- PostgreSQL
- Docker + Docker Compose
- Spring Events + @Async
- Spring Validation
- Swagger / OpenAPI
- JUnit, Mockito, MockMvc

---

## 🧠 Solution Strategy

The solution was designed to meet the following core requirements:

### ✅ Dynamic Percentage Calculation
- `POST /api/calculate` receives two input values: `num1` and `num2`.
- Applies a dynamic percentage obtained from a mocked external service.
- Uses **Redis** to cache the percentage for 30 minutes.
- If the external service fails, the last cached value is used.

### ✅ Asynchronous Logging of Requests
- Logs the request timestamp, endpoint, input parameters, response or error.
- Logging is performed asynchronously using **Spring Events + @Async** so that it doesn't delay the main response.
- Logs are stored in **PostgreSQL**.

### ✅ Paginated Request History
- `GET /api/history` allows retrieving the log history with pagination.
- A query parameter `onlyErrors=true` returns only requests that resulted in errors.

---

## 📦 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/juanalsa/smart-sum-api.git
cd smart-sum-api
```

### 2. Create a `.env` File
This file stores sensitive credentials for the database:
```env
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
```

Make sure `.env` is listed in your `.gitignore`.

### 3. Start the Infrastructure with Docker
```bash
docker-compose up -d
```

This command starts:
- PostgreSQL (for storing request logs)
- Redis (for caching the percentage value)

### 4. Run the Application
```bash
  ./mvnw spring-boot:run
```

---

## 🧪 Testing

Run all unit and integration tests:
```bash
  ./mvnw test
```

Tests include:
- **Unit tests** for business logic (calculation and error handling).
- **Integration tests** for the `/api/calculate` and `/api/history` endpoints using `MockMvc`.

---

## 📑 API Documentation

You can access the interactive Swagger UI after starting the app:

🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

This includes detailed descriptions of all available endpoints, parameters, and error responses.

---

## 📬 API Endpoints

### POST `/api/calculate`
Performs a sum of `num1` and `num2`, then applies a dynamic percentage to the result.

**Request Body:**
```json
{
  "num1": 10,
  "num2": 20
}
```

**Response:**
```json
{
  "result": 33.0
}
```

**Possible Status Codes:**
- `200 OK`: Calculation completed successfully.
- `400 Bad Request`: Missing, invalid, or non-numeric parameters.
  - Example:
    ```json
    {
      "error": "Invalid input: num1 and num2 must be numeric (BigDecimal)"
    }
    ```
- `503 Service Unavailable`: Percentage service unavailable and no cached value.

---

### GET `/api/history?page=0&size=10&onlyErrors=false`

Returns a paginated list of logged API requests.

**Query Parameters:**
- `page`: Page number (default: 0)
- `size`: Page size (default: 10)
- `onlyErrors`: Optional boolean (default: false). If `true`, only logs with errors are returned.

**Sample Response:**
```json
{
  "content": [
    {
      "timestamp": "2025-04-29T14:20:15",
      "endpoint": "/api/calculate",
      "parameters": "num1=10, num2=20",
      "response": "result=33.0",
      "error": null
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "size": 10,
  "number": 0,
  "first": true,
  "last": true
}
```

---

## 🗂 Project Structure

```
src/main/java/com/smartsum/api/
├── config/                # Configuration classes (Redis cache, Async executor)
├── controller/            # REST controllers (API entrypoints)
├── dto/                   # Request and response Data Transfer Objects (DTOs)
├── entity/                # JPA entity models (e.g., LogEntry)
├── event/                 # Custom Spring Events and async listeners
├── exception/             # Custom exception classes and global exception handler
├── repository/            # JPA repositories for data access
├── service/               # Business logic and service layer (e.g., CalculationService)
└── SmartSumApiApplication.java   # Main Spring Boot application entry point
```

```
test/java/com/smartsum/api/
├── controller/            # Integration tests for controllers using MockMvc
├── service/               # Unit tests for service layer
```

---

## 🧠 Notes

- The dynamic percentage is currently retrieved from a hardcoded mock but can be replaced with a real external API.
- The asynchronous logging system can easily be scaled using messaging technologies like RabbitMQ or Kafka in distributed environments.
- Error handling is centralized and includes support for:
    - Validation errors (`400 Bad Request`)
    - Service unavailability (`503`)
    - Internal server errors (`500`)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## ✉️ Contact

For questions or suggestions, feel free to reach out:

📧 juanalsa@gmail.com  
🌐 [LinkedIn](https://www.linkedin.com/in/julian-alvarado-sanchez)
