# 🚀 An Choi Việt Nam Backend - Quick Reference for AI Developers

## ⚡ Project at a Glance

- **Language**: Java 8 (compiled to Java 11)
- **Framework**: Spring Boot 2.7.3
- **Database**: MySQL 5.7+
- **Port**: 8888
- **Auth**: JWT (JJWT 0.9.1)
- **Base Package**: `com.anchoi`

---

## 🔑 Essential Commands

### Start Development
```bash
cd /Users/Shared/Code/an_choi_viet_nam/backend
mvn clean install -DskipTests
mvn spring-boot:run
```

### Build JAR
```bash
mvn clean package -DskipTests
java -jar target/an-choi-api-0.0.1-SNAPSHOT.jar
```

### Quick Maven Commands
```bash
mvn compile              # Compile source
mvn test                 # Run tests
mvn clean                # Clean build
mvn dependency:tree      # Show dependencies
mvn -X spring-boot:run   # Debug mode
```

---

## 📁 Directory Structure Quick Lookup

```
backend/
├── AI_DEVELOPER_GUIDE.md           ← Full development guide
├── API_DEVELOPMENT_GUIDE.md        ← API patterns & examples
├── DATABASE_AND_TROUBLESHOOTING.md ← DB schema & fixes
├── QUICK_REFERENCE.md              ← This file
├── pom.xml                         ← Dependencies
├── src/main/java/com/anchoi/
│   ├── entity/                     ← Database models
│   ├── repository/                 ← Data access layer
│   ├── service/                    ← Business logic
│   ├── controllers/                ← REST endpoints
│   │   ├── admin/                  ← Admin APIs
│   │   └── app/                    ← App user APIs
│   ├── request/                    ← Request DTOs
│   ├── response/                   ← Response DTOs
│   ├── security/                   ← JWT & auth
│   ├── config/                     ← Configuration
│   ├── common/                     ← Utilities
│   └── SpringBootAnchoiApplication.java
└── src/main/resources/
    └── application.properties       ← Config file
```

---

## 🛠️ Adding a New Feature (5 Steps)

### 1. Create Entity
```java
// src/main/java/com/anchoi/entity/Author.java
package com.anchoi.entity;
import lombok.*;
import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "author")
public class Author extends BaseEntity {
    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;
}
```

### 2. Create Repository
```java
// src/main/java/com/anchoi/repository/author/AuthorRepository.java
package com.anchoi.repository.author;
import com.anchoi.entity.Author;
import com.anchoi.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CommonRepository<Author> {
    java.util.Optional<Author> findByEmail(String email);
}
```

### 3. Create Request/Response DTOs
```java
// Request
@Data
public class AuthorRequest {
    private String id;
    @NotBlank(message = "Name required")
    private String name;
    @Email
    private String email;
}

// Response
@Data
public class AuthorResponse {
    private String id;
    private String name;
    private String email;
    private java.util.Date createdDate;
}
```

### 4. Create Service
```java
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    
    public AuthorService(AuthorRepository repo, ModelMapper mapper) {
        this.authorRepository = repo;
        this.modelMapper = mapper;
    }
    
    public AuthorResponse create(AuthorRequest req) throws BusinessException {
        if (authorRepository.findByEmail(req.getEmail()).isPresent())
            throw new BusinessException("400", "Email exists");
        Author author = modelMapper.map(req, Author.class);
        return modelMapper.map(authorRepository.save(author), AuthorResponse.class);
    }
    
    public AuthorResponse getById(String id) throws BusinessException {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new BusinessException("404", "Not found"));
        return modelMapper.map(author, AuthorResponse.class);
    }
    
    public List<AuthorResponse> getAll() {
        return authorRepository.findAll().stream()
            .map(a -> modelMapper.map(a, AuthorResponse.class))
            .collect(java.util.stream.Collectors.toList());
    }
    
    public AuthorResponse update(AuthorRequest req) throws BusinessException {
        Author author = authorRepository.findById(req.getId())
            .orElseThrow(() -> new BusinessException("404", "Not found"));
        modelMapper.map(req, author);
        return modelMapper.map(authorRepository.save(author), AuthorResponse.class);
    }
    
    public void delete(String id) throws BusinessException {
        if (!authorRepository.existsById(id))
            throw new BusinessException("404", "Not found");
        authorRepository.deleteById(id);
    }
}
```

### 5. Create Controller
```java
@RestController
@RequestMapping("/api/admin/author")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthorController {
    private final AuthorService authorService;
    
    public AuthorController(AuthorService service) {
        this.authorService = service;
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody AuthorRequest req) {
        try {
            return ResponseEntity.status(201).body(ResponseData.ok(authorService.create(req)));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ResponseData.error(null, e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ResponseData.ok(authorService.getAll()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(ResponseData.ok(authorService.getById(id)));
        } catch (BusinessException e) {
            return ResponseEntity.status(404).body(ResponseData.error(null, e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody AuthorRequest req) {
        try {
            req.setId(id);
            return ResponseEntity.ok(ResponseData.ok(authorService.update(req)));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(ResponseData.error(null, e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            authorService.delete(id);
            return ResponseEntity.ok(ResponseData.ok(null));
        } catch (BusinessException e) {
            return ResponseEntity.status(404).body(ResponseData.error(null, e.getMessage()));
        }
    }
}
```

---

## 🔗 API Endpoint Patterns

### Standard CRUD Endpoints
```
GET    /api/{resource}           → List all (public)
POST   /api/admin/{resource}     → Create (admin only)
GET    /api/{resource}/{id}      → Get one (public)
PUT    /api/admin/{resource}/{id}→ Update (admin only)
DELETE /api/admin/{resource}/{id}→ Delete (admin only)
```

### Request/Response Format
```json
// Request body (POST/PUT)
{
  "id": "optional-for-updates",
  "name": "value",
  "email": "user@example.com"
}

// Successful response (200, 201)
{
  "message": "OK",
  "status": 200,
  "data": { /* response object */ }
}

// Error response (400, 401, 404, etc)
{
  "message": "Error description",
  "status": 400,
  "data": null
}
```

---

## 🔐 Authentication Quick Guide

### Login to Get Token
```bash
curl -X POST http://localhost:8888/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Use Token in Requests
```bash
curl -X GET http://localhost:8888/api/admin/author \
  -H "Authorization: Bearer eyJhbGciOi..."
```

### Role-Based Access
```java
@PreAuthorize("hasRole('ROLE_ADMIN')")        // Admin only
@PreAuthorize("hasRole('ROLE_MODERATOR')")    // Moderator only
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')") // Either
@PreAuthorize("isAuthenticated()")            // Any authenticated user
```

---

## 💾 Database Operations Quick Ref

### Connect to MySQL
```bash
mysql -u anchoi12$1 -p 3K4xMdb@59ul -h 192.168.1.202 an_choi
# Or localhost:
mysql -u root -p -h localhost an_choi
```

### Essential SQL
```sql
-- Show tables
SHOW TABLES;

-- Show table structure
DESC item;

-- Check indexes
SHOW INDEX FROM item;

-- Create index for performance
CREATE INDEX idx_name ON table_name(column_name);

-- Reset auto-increment (if issues)
ALTER TABLE user AUTO_INCREMENT = 1;

-- Check foreign keys
SELECT CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME 
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_NAME = 'item';
```

### Useful Queries
```sql
-- Insert default language
INSERT INTO language(id, code, name, is_active, created_date, created_by)
VALUES(UUID(), 'vi', 'Tiếng Việt', 1, NOW(), 'system');

-- Insert default roles
INSERT INTO role_user(id, name, created_date, created_by)
VALUES
  (UUID(), 'ROLE_USER', NOW(), 'system'),
  (UUID(), 'ROLE_MODERATOR', NOW(), 'system'),
  (UUID(), 'ROLE_ADMIN', NOW(), 'system');

-- Find by username
SELECT * FROM user WHERE username = 'admin';

-- Check last inserted ID
SELECT LAST_INSERT_ID();

-- Get count of records
SELECT COUNT(*) as total FROM item;
```

---

## 🧪 Testing API Endpoints

### Using cURL (Terminal)
```bash
# Set token variable
TOKEN=$(curl -s -X POST http://localhost:8888/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | jq -r '.data.token')

# Use token
curl -X GET http://localhost:8888/api/admin/author \
  -H "Authorization: Bearer $TOKEN"

# POST with data
curl -X POST http://localhost:8888/api/admin/author \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@example.com"}'
```

### Using VS Code REST Client
```http
@baseUrl = http://localhost:8888
@token = 

### Login
POST {{baseUrl}}/api/auth/signin
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

### Get all authors (copy token from response above)
GET {{baseUrl}}/api/author
Authorization: Bearer {{token}}

### Create author
POST {{baseUrl}}/api/admin/author
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "name": "Jane Doe",
  "email": "jane@example.com"
}
```

---

## 🐛 Common Errors & Fixes

| Error | Cause | Fix |
|-------|-------|-----|
| `Unknown database 'an_choi'` | DB doesn't exist | `CREATE DATABASE an_choi;` |
| `Access denied for user` | Wrong credentials | Check `application.properties` |
| `Table doesn't exist` | Hibernate DDL disabled | Set `ddl-auto=update` |
| `Duplicate entry` | Unique constraint violated | Check for existing data |
| `Foreign key constraint fails` | Parent record missing | Insert parent first |
| `Connection refused` | MySQL not running | `mysql.server start` (macOS) |
| `401 Unauthorized` | Missing/invalid token | Include valid JWT in header |
| `403 Forbidden` | Insufficient role | Check @PreAuthorize annotation |
| `Data too long for column` | String exceeds VARCHAR limit | Reduce string or increase column size |
| `LazyInitializationException` | Accessing lazy relationship | Use JOIN FETCH or FetchType.EAGER |

---

## 📝 Code Patterns to Follow

### Entity Pattern
```java
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "table_name")
public class YourEntity extends BaseEntity {
    // Inherits: id, createdDate, createdBy, updatedDate, updatedBy
    
    @Column(name = "field_name")
    private String fieldName;
}
```

### Service Pattern
```java
@Service
public class YourService {
    private final YourRepository repository;
    private final ModelMapper modelMapper;
    
    public YourService(YourRepository repo, ModelMapper mapper) {
        this.repository = repo;
        this.modelMapper = mapper;
    }
    
    public YourResponse create(YourRequest request) throws BusinessException {
        // Validate
        // Map DTO to entity
        // Save to database
        // Return response
    }
}
```

### Controller Pattern
```java
@RestController
@RequestMapping("/api/admin/resource")
@CrossOrigin(origins = "*", maxAge = 3600)
public class YourController {
    private final YourService service;
    
    public YourController(YourService service) {
        this.service = service;
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody YourRequest req) {
        try {
            return ResponseEntity.status(201)
                .body(ResponseData.ok(service.create(req)));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest()
                .body(ResponseData.error(null, e.getMessage()));
        }
    }
}
```

### Exception Handling Pattern
```java
// Throw
throw new BusinessException("400", "Descriptive error message");

// Catch & respond
try {
    service.operation();
} catch (BusinessException e) {
    return ResponseEntity.status(Integer.parseInt(e.getCode()))
        .body(ResponseData.error(null, e.getMessage()));
}
```

---

## ✅ Checklist Before Commit

- [ ] Code compiles: `mvn clean compile`
- [ ] Follows naming conventions (camelCase for Java, snake_case for DB)
- [ ] Entity extends BaseEntity
- [ ] DTOs have validation annotations
- [ ] Service handles BusinessException
- [ ] Controller returns ResponseData wrapper
- [ ] Authentication/authorization checks added
- [ ] Tests pass (if applicable)
- [ ] No hardcoded passwords/secrets
- [ ] Database migrations documented
- [ ] Comments added for complex logic
- [ ] Error messages are clear and helpful

---

## 🔍 Key Configuration File Locations

| File | Purpose |
|------|---------|
| `application.properties` | Database, JWT, file paths, logging |
| `pom.xml` | Dependencies & build config |
| `WebSecurityConfig.java` | Security rules & JWT filters |
| `JwtUtils.java` | Token generation & validation |
| `BaseEntity.java` | Common fields (id, timestamps) |
| `ResponseData.java` | API response wrapper |
| `BusinessException.java` | Custom exception |

---

## 🚀 Next Steps to Master This Backend

1. **Read Full Guides**: Open `AI_DEVELOPER_GUIDE.md` for complete architecture
2. **Study Examples**: Check `API_DEVELOPMENT_GUIDE.md` for detailed patterns
3. **Database**: Review `DATABASE_AND_TROUBLESHOOTING.md` for schema
4. **Test Endpoints**: Use VS Code REST Client to test your changes
5. **Debug**: Use Spring Boot DevTools with `mvn spring-boot:run` for live reload
6. **Profile Code**: Use IDE profiler to find performance bottlenecks
7. **Add Logging**: Use `org.slf4j.Logger` for debugging

---

## 💡 Pro Tips

- Always use `@Valid` on request parameters for automatic validation
- Wrap all API responses in `ResponseData<T>` for consistency
- Use `ModelMapper` to convert between entities and DTOs
- Add indexes to frequently queried columns for performance
- Use `@Transactional` in services for database transactions
- Keep business logic in services, not controllers
- Test error scenarios, not just happy path
- Log important operations for debugging production issues
- Use `Optional.ofNullable()` to handle null values safely
- Commit small changes frequently with clear messages
