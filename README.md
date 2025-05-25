# Document Manager

A Spring Boot web application that provides a RESTful API for managing documents and user authentication with JWT. The application uses Spring Web, Spring Data JPA, Spring Security, and integrates with a MySQL database.

## Project Structure

```
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jktech/assignment/documentmanager/
│   │   │       ├── DocumentmanagerApplication.java
│   │   │       ├── config/
│   │   │       │   ├── JwtAuthFilter.java
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── SwaggerConfig.java
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── DocumentController.java
│   │   │       │   └── QnAController.java
│   │   │       ├── dto/
│   │   │       │   └── LoginRequest.java
│   │   │       ├── model/
│   │   │       │   ├── Document.java
│   │   │       │   └── User.java
│   │   │       ├── repository/
│   │   │       │   ├── DocumentRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── service/
│   │   │       │   ├── DocumentService.java
│   │   │       │   ├── UserDetailsImpl.java
│   │   │       │   └── UserDetailsServiceImpl.java
│   │   │       └── util/
│   │   │           └── JwtUtil.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/com/jktech/assignment/documentmanager/
│           ├── DocumentmanagerApplicationTests.java
│           └── service/
│               ├── DocumentServiceTest.java
│               ├── UserDetailsImplTest.java
│               └── UserDetailsServiceImplTest.java
└── target/
```

## Prerequisites
- Java 11 or later
- Maven 3.6 or later
- MySQL database

## Setup and Installation

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/your-repo-name.git
cd documentmanager
```

### 2. Configure the Database
Edit `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:mysql://<hostname>:3306/<database-name>
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
```

### 3. Configure JWT
Add the following to `src/main/resources/application.properties`:
```
jwt.secret=<your-secret-key>
jwt.expirationMs=86400000
```

### 4. Build the Project
```bash
mvn clean package
```

### 5. Run the Application
```bash
java -jar target/documentmanager-0.0.1-SNAPSHOT.jar
```

### 6. API Documentation (Swagger UI)
After starting the application, you can access the Swagger UI for interactive API documentation at:

```
http://localhost:8080/swagger-ui/
```

This interface allows you to explore and test all available API endpoints.

## API Endpoints
- `GET /api/documents/search`: Search documents by keyword
- `GET /api/qna/search`: Q & A keyword search
- `GET /api/documents` : Filter documents by author and file type
- `POST /auth/register`: For User registration
- `POST /auth/login`: For User login
- `POST /api/documents/upload`: For uploading documents

> Replace `[Description]` with the actual purpose of each endpoint.

## Troubleshooting
- Spring Boot documentation: https://docs.spring.io/spring-boot/docs/current/reference/html/
- Spring Framework documentation: https://docs.spring.io/spring/docs/current/spring-framework-reference/
- Project issue tracker: https://github.com/your-username/your-repo-name/issues

## License
[Specify your license here]

