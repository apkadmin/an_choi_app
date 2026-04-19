# 🚀 An Choi Việt Nam - Backend AI Developer Guide

## 📋 Table of Contents
1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Project Structure](#project-structure)
4. [Architecture](#architecture)
5. [Setup & Installation](#setup--installation)
6. [Core Concepts](#core-concepts)
7. [How to Add New Features](#how-to-add-new-features)
8. [Database Schema](#database-schema)
9. [API Conventions](#api-conventions)
10. [Security](#security)
11. [Common Development Patterns](#common-development-patterns)
12. [Troubleshooting](#troubleshooting)

---

## 📖 Project Overview

**An Choi Việt Nam** is a Spring Boot REST API for a Vietnamese gaming/learning platform with the following features:

- User authentication & authorization (JWT-based)
- Content management (categories, items, media, posts, questions)
- Geographical data (provinces, districts with i18n support)
- Gaming features (jigsaw puzzles, questions, game users)
- File upload & video management
- Search functionality
- Multi-language support (I18n)
- Role-based access control (User, Moderator, Admin)

**API Port**: 8888 (configured in `application.properties`)
**Base Package**: `com.anchoi`
**Database**: MySQL 5.7+

---

## 🛠 Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 1.8 | Programming language |
| **Spring Boot** | 2.7.3 | Web framework |
| **Spring Security** | Latest | Authentication & authorization |
| **Spring Data JPA** | Latest | ORM & database access |
| **MySQL** | 5.7+ | Relational database |
| **JWT (JJWT)** | 0.9.1 | Token-based authentication |
| **Lombok** | 1.18.20 | Boilerplate reduction |
| **ModelMapper** | 3.1.1 | Object mapping (DTO ↔ Entity) |
| **Undertow** | Latest | Embedded web server |
| **Maven** | 3.6+ | Build tool |
| **Commons Lang3** | 3.12.0 | String utilities |
| **Gson** | Latest | JSON processing |
| **Tika** | 2.5.0 | MIME type detection |

---

## 📁 Project Structure

```
backend/
├── src/main/java/com/anchoi/
│   ├── SpringBootAnchoiApplication.java
│   │
│   ├── config/                              # Configuration classes
│   │   ├── ApplicationConfiguration.java    # Async config
│   │   ├── BusinessException.java          # Custom exception
│   │   ├── FileUploadExceptionAdvice.java  # Global error handler
│   │   ├── MimeTypes.java                  # MIME type constants
│   │   └── ServiceRegister.java            # Service registration
│   │
│   ├── security/                            # Authentication & authorization
│   │   ├── WebSecurityConfig.java          # Main security config
│   │   ├── jwt/
│   │   │   ├── JwtUtils.java               # JWT generation & validation
│   │   │   ├── AuthTokenFilter.java        # JWT request filter
│   │   │   └── AuthEntryPointJwt.java      # Unauthorized handler
│   │   └── services/
│   │       └── UserDetailsServiceImpl.java  # Custom user loader
│   │
│   ├── entity/                              # JPA entities
│   │   ├── BaseEntity.java                 # Abstract base with audit fields
│   │   ├── User.java                       # User account
│   │   ├── RoleUser.java                   # Role assignment
│   │   ├── Category.java                   # Categories
│   │   ├── CategoryI18n.java               # Category translations
│   │   ├── Province.java                   # Provinces
│   │   ├── ProvinceI18n.java               # Province translations
│   │   ├── District.java                   # Districts
│   │   ├── DistrictI18n.java               # District translations
│   │   ├── Item.java                       # Game items
│   │   ├── ItemI18n.java                   # Item translations
│   │   ├── Media.java                      # Media files
│   │   ├── MediaI18n.java                  # Media translations
│   │   ├── Question.java                   # Quiz questions
│   │   ├── QuestionDetail.java             # Question answers
│   │   ├── JigsawDataEntity.java           # Jigsaw puzzles
│   │   ├── JigsawDetailEntity.java         # Jigsaw details
│   │   ├── GameUser.java                   # Game user progress
│   │   ├── Post.java                       # Blog posts
│   │   ├── PointVietnamEntity.java         # Location points
│   │   ├── Language.java                   # Language settings
│   │   └── ffmpeg/                         # FFmpeg entities
│   │
│   ├── repository/                          # Data access layer
│   │   ├── CommonRepository.java           # Generic interface
│   │   ├── CommonRepositoryImpl.java        # Generic implementation
│   │   ├── manage/                         # User repositories
│   │   ├── category/                       # Category repositories
│   │   ├── district/                       # District repositories
│   │   ├── province/                       # Province repositories
│   │   ├── item/                           # Item repositories
│   │   ├── media/                          # Media repositories
│   │   ├── post/                           # Post repositories
│   │   ├── question/                       # Question repositories
│   │   ├── jigsaw/                         # Jigsaw repositories
│   │   ├── game/                           # Game repositories
│   │   └── point/                          # Point repositories
│   │
│   ├── service/                             # Business logic layer
│   │   ├── UserService.java                # User management
│   │   ├── CategoryService.java
│   │   ├── ProvinceService.java
│   │   ├── DistrictService.java
│   │   ├── ItemService.java
│   │   ├── MediaService.java
│   │   ├── PostService.java
│   │   ├── QuestionService.java
│   │   ├── JigsawService.java
│   │   ├── GameUserService.java
│   │   ├── PointVietnamService.java
│   │   ├── LanguageService.java
│   │   ├── SearchService.java
│   │   ├── SyncService.java
│   │   ├── RoleUserService.java
│   │   ├── UploadVideoService.java
│   │   └── impl/                           # Service implementations
│   │
│   ├── controllers/                         # REST endpoints
│   │   ├── admin/                          # Admin API
│   │   │   ├── AuthController.java         # Auth endpoints
│   │   │   ├── UserController.java
│   │   │   ├── CategoryController.java
│   │   │   ├── ProvinceController.java
│   │   │   ├── DistrictController.java
│   │   │   ├── ItemController.java
│   │   │   ├── MediaControler.java
│   │   │   ├── PostController.java
│   │   │   ├── QuestionController.java
│   │   │   ├── JigsawController.java
│   │   │   ├── PointVietNamController.java
│   │   │   ├── RoleController.java
│   │   │   └── FilesController.java
│   │   ├── app/                            # App user API
│   │   │   └── AppController.java
│   │   ├── LanguageController.java
│   │   ├── PostController.java
│   │   └── SyncData.java
│   │
│   ├── request/                             # Request DTOs
│   │   ├── LoginRequest.java
│   │   ├── SignupRequest.java
│   │   ├── UserRequest.java
│   │   ├── ChangePasswordRequest.java
│   │   ├── ProvinceRequest.java
│   │   ├── DistrictRequest.java
│   │   ├── ItemRequest.java
│   │   ├── MediaRequest.java
│   │   ├── MediaUploadRequest.java
│   │   ├── QuestionRequest.java
│   │   ├── I18nRequest.java
│   │   └── MediaDesRequest.java
│   │
│   ├── response/                            # Response DTOs
│   │   ├── ResponseData.java               # Generic wrapper
│   │   ├── MessageResponse.java
│   │   ├── JwtResponse.java
│   │   ├── UserResponse.java
│   │   ├── UserInfoResponse.java
│   │   ├── CategoryResponse.java
│   │   ├── ProvinceResponse.java
│   │   ├── ProvinceI18nResponse.java
│   │   ├── DistrictResponse.java
│   │   ├── DistrictI18nResponse.java
│   │   ├── ItemResponse.java
│   │   ├── ItemAppResponse.java
│   │   ├── ItemI18nResponse.java
│   │   ├── MediaResponse.java
│   │   ├── PostResponse.java
│   │   ├── QuestionResponse.java
│   │   ├── SearchResponse.java
│   │   ├── AreaResponse.java
│   │   ├── PointVietnamV1Response.java
│   │   └── I18nResponse.java
│   │
│   ├── http/                                # HTTP utilities
│   │   ├── HttpClient.java
│   │   ├── HttpRequest.java
│   │   ├── HttpResponse.java
│   │   ├── HttpMethod.java
│   │   ├── HttpParameter.java
│   │   ├── HttpParameterSet.java
│   │   ├── HttpParameterSetParser.java
│   │   └── HttpRequestFactory.java
│   │
│   └── common/                              # Utilities
│       ├── CommonUtils.java
│       └── FileUtils.java
│
└── src/main/resources/
    ├── application.properties
    └── static/
```

---

## 🏗 Architecture

### Layered Architecture

```
┌─────────────────────────────────────┐
│     REST Controllers                │ HTTP Entry Points
│ @RestController @RequestMapping     │
└────────────────┬────────────────────┘
                 │ receives requests
┌────────────────▼────────────────────┐
│     Services (@Service)             │ Business Logic
│  (UserService, ItemService, etc.)   │
└────────────────┬────────────────────┘
                 │ calls repositories
┌────────────────▼────────────────────┐
│  Repositories (@Repository)         │ Data Access (JPA)
│ (UserRepository, ItemRepository...)  │
└────────────────┬────────────────────┘
                 │ maps to/from
┌────────────────▼────────────────────┐
│     Entities (@Entity)              │ Database Models
│ (User, Category, Item, Province...) │
└─────────────────────────────────────┘
```

### Request/Response Flow

```
CLIENT REQUEST
  ↓
REST Controller (@RestController)
  - Receive HTTP request
  - Validate input (@Valid, custom validators)
  - Check authentication (JWT filter)
  - Extract parameters
  ↓
Service Layer (@Service)
  - Apply business logic
  - Call repositories for DB operations
  - Validate business rules
  - Transform entities to DTOs
  ↓
Repository Layer (@Repository extends CommonRepository)
  - Execute JPA/Hibernate queries
  - CRUD operations on database
  - Return entities
  ↓
Response DTO (Response classes)
  - Wrap data in ResponseData<T>
  - Add status and message
  ↓
JSON Response
  - Serialize to JSON
  - Return to client
```

### Security Flow (JWT)

```
1. LOGIN
   POST /api/auth/signin with credentials
   → AuthController.login()
   → AuthenticationManager.authenticate()
   → JwtUtils.generateJwtToken() creates JWT
   → Returns JwtResponse with token

2. AUTHENTICATED REQUEST
   GET /api/resource with Authorization: Bearer <token>
   → AuthTokenFilter extracts token
   → JwtUtils.validateJwtToken() validates signature & expiry
   → JwtUtils.getUserNameFromJwtToken() extracts username
   → UserDetailsServiceImpl loads user & roles
   → SecurityContextHolder stores authentication
   → Request allowed to proceed

3. UNAUTHORIZED REQUEST
   Missing/invalid token
   → AuthEntryPointJwt.commence()
   → Returns 401 Unauthorized
```

---

## 🚀 Setup & Installation

### Prerequisites
- Java 8+ (project uses Java 1.8 but compiled for Java 11)
- Maven 3.6+
- MySQL 5.7+
- Git

### Step 1: Clone Repository
```bash
cd /Users/Shared/Code/an_choi_viet_nam/backend
```

### Step 2: Configure Database Connection
Edit `src/main/resources/application.properties`:

```properties
# Current configuration
spring.datasource.url=jdbc:mysql://192.168.1.202:3306/an_choi?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=anchoi12$1
spring.datasource.password=3K4xMdb@59ul

# For local development (localhost)
# spring.datasource.url=jdbc:mysql://localhost:3306/an_choi?allowPublicKeyRetrieval=true&useSSL=false
# spring.datasource.username=root
# spring.datasource.password=yourpassword

# JPA/Hibernate Settings
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto=update  # auto-create tables

# JWT Configuration
anchoi.app.jwtSecret=anchoiSecretKey
anchoi.app.jwtExpirationMs=6000000    # 6000 seconds ≈ 1.67 hours

# File Upload Configuration
spring.servlet.multipart.max-file-size=600000MB
spring.servlet.multipart.max-request-size=600000MB
base.uri=/var/www/html
base.folder=/uploads
app.video-folder=/var/www/html/upload
```

### Step 3: Initialize Database
Connect to MySQL and run:

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS an_choi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE an_choi;

-- Create roles table and insert default roles
-- (Tables will be auto-created by Hibernate with ddl-auto=update)

-- Insert default roles after application starts
INSERT INTO role_user(id, name) VALUES(UUID(), 'ROLE_USER');
INSERT INTO role_user(id, name) VALUES(UUID(), 'ROLE_MODERATOR');
INSERT INTO role_user(id, name) VALUES(UUID(), 'ROLE_ADMIN');
```

### Step 4: Build Project
```bash
mvn clean install -DskipTests
```

### Step 5: Run Application
```bash
# Option 1: Using Maven plugin
mvn spring-boot:run

# Option 2: Run JAR file
mvn clean package
java -jar target/an-choi-api-0.0.1-SNAPSHOT.jar

# Option 3: Using IDE (IntelliJ, Eclipse)
# Right-click SpringBootAnchoiApplication.java → Run
```

### Step 6: Verify Installation
```bash
# Health check
curl http://localhost:8888/api/test/public

# Get all languages
curl http://localhost:8888/api/language

# Swagger documentation (if available)
curl http://localhost:8888/swagger-ui.html
```

---

## 💡 Core Concepts

### 1. BaseEntity - Audit & Tracking
All entities extend `BaseEntity` providing automatic audit fields:

```java
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 36)
    private String id;  // UUID
    
    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;  // Auto-set on insert
    
    @Column(name = "created_by")
    private String createdBy;  // "system" by default
    
    @Column(name = "updated_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;  // Auto-updated on change
    
    @Column(name = "updated_by")
    private String updatedBy;  // "system" by default
    
    @PrePersist  // Executes before INSERT
    public void prePersist() {
        this.setCreatedDate(new Date());
        this.setCreatedBy("system");
    }
    
    @PreUpdate  // Executes before UPDATE
    public void preUpdate() {
        if(getCreatedDate() == null) {
            this.setCreatedDate(new Date());
        }
        this.setUpdatedDate(new Date());
        this.setUpdatedBy("system");
    }
}
```

### 2. Internationalization (I18n)
Multi-language support using separate translation entities:

```java
// Main entity
@Entity
@Table(name = "province")
public class Province extends BaseEntity {
    @Column(name = "code")
    private String code;
    
    @OneToMany(mappedBy = "provinceId", cascade = CascadeType.ALL)
    private List<ProvinceI18n> translations;
}

// Translation entity (one record per language)
@Entity
@Table(name = "province_i18n")
public class ProvinceI18n extends BaseEntity {
    @Column(name = "province_id")
    private String provinceId;  // Foreign key
    
    @Column(name = "language_id")
    private String languageId;  // Language identifier
    
    @Column(name = "name")
    private String name;  // Translated name
    
    @Column(name = "description")
    private String description;  // Translated description
}
```

**Usage**: Fetch translations with province, then filter by language in response:
```
GET /api/province
→ Returns provinces with all translations
→ Client can filter translations by languageId
```

### 3. JWT Authentication
Token-based stateless authentication:

```java
// Login: Generate token
POST /api/auth/signin
{
    "username": "user123",
    "password": "pass123"
}
→ Returns token valid for 6000 seconds

// Using token: Include in header
GET /api/resource
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...

// Token validation flow:
1. Extract token from Authorization header
2. Validate signature using secret key
3. Check expiration time
4. Extract username and roles
5. Load user details from database
6. Populate SecurityContext
```

### 4. ResponseData Wrapper - Consistent API Responses
All endpoints return data wrapped in `ResponseData` for consistency:

```java
public class ResponseData<T> {
    private String message;  // "OK" or error description
    private Integer status;  // HTTP status code (200, 400, 401, 500)
    private T data;         // Actual response payload
    
    // Helper methods
    public static <T> ResponseData ok(T data) { ... }
    public static <T> ResponseData error(T data, String message) { ... }
}

// Example responses
// Success
{
    "message": "OK",
    "status": 200,
    "data": {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "name": "Sample Item",
        "createdDate": "2024-01-15 10:30:45"
    }
}

// Error
{
    "message": "Item not found",
    "status": 400,
    "data": null
}
```

### 5. BusinessException - Custom Error Handling
Throw business errors that are automatically caught and formatted:

```java
// Throw in service
if (!userFound) {
    throw new BusinessException("400", "User not found");
}

// Caught by @ControllerAdvice
// Returned as ResponseData error response
```

### 6. Repository Pattern - Data Access
Generic `CommonRepository` provides standard CRUD:

```java
// Define repository
@Repository
public interface UserRepository extends CommonRepository<User> {
    // Inherits: save(), findById(), findAll(), delete(), etc.
    
    // Add custom queries
    Optional<User> findByUsername(String username);
    List<User> findByEmailContaining(String email);
}

// Use in service
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User getById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("404", "User not found"));
    }
    
    public User create(User user) {
        return userRepository.save(user);
    }
}
```

---

## 🆕 How to Add New Features

### Complete Example: Add New "Author" Entity with CRUD

#### Step 1: Create Entity Class
Create `src/main/java/com/anchoi/entity/Author.java`:

```java
package com.anchoi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "author")
public class Author extends BaseEntity {
    
    @Basic
    @Column(name = "name")
    private String name;
    
    @Basic
    @Column(name = "biography")
    private String biography;
    
    @Basic
    @Column(name = "email")
    private String email;
    
    @Basic
    @Column(name = "phone")
    private String phone;
    
    @Basic
    @Column(name = "country")
    private String country;
}
```

#### Step 2: Create Repository
Create `src/main/java/com/anchoi/repository/author/AuthorRepository.java`:

```java
package com.anchoi.repository.author;

import com.anchoi.entity.Author;
import com.anchoi.repository.CommonRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CommonRepository<Author> {
    
    // Custom queries
    Optional<Author> findByEmail(String email);
    
    List<Author> findByNameContainingIgnoreCase(String name);
    
    Optional<Author> findByPhone(String phone);
}
```

#### Step 3: Create Request DTO
Create `src/main/java/com/anchoi/request/AuthorRequest.java`:

```java
package com.anchoi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequest {
    
    private String id;  // For updates
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String biography;
    
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;
    
    private String phone;
    
    private String country;
}
```

#### Step 4: Create Response DTO
Create `src/main/java/com/anchoi/response/AuthorResponse.java`:

```java
package com.anchoi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {
    
    private String id;
    private String name;
    private String biography;
    private String email;
    private String phone;
    private String country;
    private Date createdDate;
    private Date updatedDate;
}
```

#### Step 5: Create Service
Create `src/main/java/com/anchoi/service/AuthorService.java`:

```java
package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.Author;
import com.anchoi.repository.author.AuthorRepository;
import com.anchoi.request.AuthorRequest;
import com.anchoi.response.AuthorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    
    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }
    
    /**
     * Create new author
     
