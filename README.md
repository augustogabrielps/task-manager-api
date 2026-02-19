# Task Manager API

A simple REST API built with Spring Boot and PostgreSQL to manage tasks.

This project is part of my backend development journey using:

- Java 21
- Spring Boot 4
- PostgreSQL 15
- Docker & Docker Compose
- Maven

---

## 📦 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Docker**
- **Docker Compose**

---

## 🏗️ Architecture Overview

The application runs locally using:

- A PostgreSQL container managed by Docker Compose
- Spring Boot running on port `8080`
- Database exposed on port `5433`

Spring Boot connects to PostgreSQL using environment variables for security.

---

## 🔐 Environment Variables

This project uses environment variables instead of hardcoded credentials.

Create a `.env` file in the root of the project:

DB_URL=jdbc:postgresql://localhost:5433/taskdb
DB_USER=taskuser
DB_PASSWORD=taskpass


⚠️ The `.env` file is ignored by Git for security reasons.

---

## 🐳 Running with Docker

Start PostgreSQL container:

docker compose up -d


Check if container is running:

docker ps


You should see:

postgres:15


---

## 🚀 Running the Application

Load environment variables:

set -a
source .env
set +a


Start Spring Boot:

./mvnw spring-boot:run


Application will run at:

http://localhost:8080


---

## 🛠 Database Connection Details

- Host: localhost
- Port: 5433
- Database: taskdb
- User: taskuser

You can connect using DBeaver or any PostgreSQL client.

---

## 📂 Project Structure

taskmanager/
├── src/
│ ├── main/
│ └── test/
├── docker-compose.yml
├── pom.xml
├── .env (ignored)
└── README.md


---

## 🧠 Learning Goals

This project was created to:

- Understand containerized databases
- Learn environment variable configuration
- Practice clean backend structure
- Prepare for production-ready setups

---

## 📈 Future Improvements

- Add Task entity and CRUD endpoints
- Implement DTO pattern
- Add validation
- Add Swagger documentation
- Add authentication (JWT)
- Deploy to cloud

---
