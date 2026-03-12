# Task Manager API

A RESTful API built with **Spring Boot and PostgreSQL** to manage tasks with filtering, pagination and validation.

This project was created as part of my backend development journey and demonstrates common patterns used in professional Java backend applications.

---

# 📦 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL 15**
- **Docker**
- **Maven**
- **Swagger / OpenAPI**
- **JUnit & Mockito**

---

# ✨ Features

The API supports full task management including:

- Create tasks
- Retrieve tasks
- Update tasks
- Delete tasks
- Filter tasks
- Pagination
- Sorting

Additional backend features implemented:

- DTO pattern
- Global exception handling
- Input validation
- API documentation with Swagger
- Unit tests for controller and service layers

---

# 🌐 API Endpoints

Base URL: http://localhost:8080/


| Method | Endpoint | Description |
|------|------|------|
| POST | `/tasks` | Create a new task |
| GET | `/tasks` | List tasks with filters and pagination |
| GET | `/tasks/{id}` | Get a task by id |
| PUT | `/tasks/{id}` | Update a task |
| DELETE | `/tasks/{id}` | Delete a task |

```
Example request: GET /tasks?status=TODO&priority=HIGH&q=spring&page=0&size=5&sort=createdAt,desc
```

---

# 📑 Swagger Documentation

Interactive API documentation is available at:
```
http://localhost:8080/swagger-ui/index.html
```

Swagger allows testing endpoints directly from the browser.

---

# 🚀 Running the Project

## 1️⃣ Configure environment variables

Create a `.env` file in the root directory:

```bash
DB_URL=jdbc:postgresql://localhost:5433/taskdb
DB_USER=taskuser
DB_PASSWORD=taskpass
```


The `.env` file is ignored by Git.

---

## 2️⃣ Start PostgreSQL container

```bash
docker compose up -d
```

Verify container:
```bash
docker ps   
```

You should see:
```bash
postgres:15
```


---

## 3️⃣ Run the application
```bash
./mvnw spring-boot:run
```


Application will be available at:
```aiignore
http://localhost:8080/
```

---
# 🧪 Running Tests

Run all tests with:
```bash
mvn test
```

The project includes:

- Controller tests
- Service unit tests
- Mockito-based repository mocking
---
# 📂 Project Structure
```markdown
taskmanager
├── controller
├── service
├── repository
├── domain
├── dto
├── specification
├── exception
└── test
```

Architecture follows the layered pattern:
```markdown
Controller → Service → Repository → Database
```

---
# 🔮 Future Improvements

Possible future extensions:

- Authentication with JWT
- User ownership of tasks
- Task categories
- CI/CD pipeline

---
