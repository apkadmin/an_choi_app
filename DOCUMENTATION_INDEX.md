# 📚 An Choi Việt Nam Backend - Documentation Index

## Overview
This backend is a Spring Boot REST API for a Vietnamese gaming/learning platform. Use this index to navigate the comprehensive documentation written for AI developers.

---

## 📖 Documentation Files

### 1. **QUICK_REFERENCE.md** ⭐ START HERE
**For**: Quick lookup and getting started
**Contains**:
- Project overview at a glance
- Essential commands (build, run, test)
- 5-step guide to add new features
- API endpoint patterns with examples
- Authentication quick guide
- Database connection info
- Common errors & quick fixes
- Code patterns to follow
- Pre-commit checklist

**Read this first if you want to**: Start coding immediately

---

### 2. **AI_DEVELOPER_GUIDE.md** 📖 COMPREHENSIVE GUIDE
**For**: Deep understanding of the project
**Contains**:
- Complete project overview and features
- Technology stack & dependencies
- Full project structure explanation (80+ files)
- Layered architecture diagram
- Request/response flow visualization
- Security flow (JWT authentication)
- Setup & installation (6 steps)
- Database initialization
- Core concepts explained:
  - BaseEntity (audit fields)
  - I18n (internationalization)
  - JWT Authentication
  - ResponseData wrapper
  - BusinessException handling
  - Repository pattern
- Complete example: Adding "Author" entity with CRUD
  - Entity creation
  - Repository setup
  - Request/Response DTOs
  - Service implementation (partially shown)

**Read this if you want to**: Understand the entire architecture

---

### 3. **API_DEVELOPMENT_GUIDE.md** 🔌 API PATTERNS
**For**: Building and consuming API endpoints
**Contains**:
- API endpoint structure & conventions
- Current API routes breakdown
- CRUD Operations Pattern with examples:
  - CREATE (POST) with curl example
  - READ (GET) - all and by ID
  - UPDATE (PUT) with examples
  - DELETE with examples
- Authentication & Authorization (4 subsections):
  - User login endpoint
  - Using tokens in requests
  - Role-based access control (@PreAuthorize)
  - User registration
- Request/Response Examples (4 detailed examples):
  - Get items with translations
  - Create item with I18n
  - File upload
  - Search functionality
- Error Handling:
  - Error response format
  - Common error codes table
  - Validation errors
  - Authentication errors
  - Authorization errors
- Common Patterns (5 patterns):
  - Get single resource with related data
  - List with pagination & filtering
  - Update with partial data
  - Bulk operations
  - Async operations
- Testing API (3 methods):
  - Using cURL with token management
  - Using Postman with environment setup
  - Using VS Code REST Client (.http file)
- Best Practices (10 items):
  - Request validation
  - Consistent response format
  - Error handling
  - JWT token management
  - Pagination for large datasets
  - HTTP status codes
  - API versioning
  - Logging important operations
  - Security best practices
  - API documentation comments
- Quick reference checklist

**Read this if you want to**: Build or test API endpoints

---

### 4. **DATABASE_AND_TROUBLESHOOTING.md** 🗄️ DATABASE GUIDE
**For**: Database schema and problem-solving
**Contains**:

**Database Schema Section** (20 tables with full SQL):
- User table
- RoleUser table
- Category & CategoryI18n tables
- Province & ProvinceI18n tables
- District & DistrictI18n tables
- Item & ItemI18n tables
- Media & MediaI18n tables
- Question & QuestionDetail tables
- Post table
- JigsawDataEntity & JigsawDetailEntity
- GameUser table
- PointVietnamEntity table
- Language table

**Entity Relationships**:
- Relationship diagram (visual)
- Key relationships explained (10+ relationships)

**Database Initialization**:
- Complete SQL setup script
- MySQL Workbench instructions
- Command line setup
- Spring Boot automatic creation info

**Troubleshooting Guide** (10 common issues):
1. Unknown database error - causes & solutions
2. Access denied error - user permissions
3. Table doesn't exist - DDL auto settings
4. Duplicate entry - unique constraints
5. Foreign key constraint fails - references
6. Data too long for column - VARCHAR limits
7. No suitable driver found - JDBC setup
8. Connection refused - MySQL server issues
9. Hibernate mapping errors - type issues
10. Lazy initialization exception - transaction issues

**Performance Tips** (5 strategies):
- Index important columns with SQL examples
- Pagination for large result sets
- JOIN FETCH to avoid N+1 queries
- Caching static data
- Projection for specific fields

**Common Queries** (database snippets for):
- User queries (with roles, by date, count)
- Item queries (with translations, by category, popular)
- Location queries (districts, landmarks, nearby)
- Game progress queries (user progress, popular items, averages)
- Content queries (posts, questions, quizzes)

**Read this if you want to**: Understand database structure or fix database issues

---

### 5. **DOCUMENTATION_INDEX.md** (This File)
**For**: Navigation and overview of all documentation
**Contains**:
- Overview of all 5 documentation files
- Quick navigation guide
- How to use the documentation
- When to read each file

---

## 🗺️ Navigation Guide

### I want to...

**...start development immediately**
→ Read: `QUICK_REFERENCE.md`

**...understand the entire architecture**
→ Read: `AI_DEVELOPER_GUIDE.md` then `API_DEVELOPMENT_GUIDE.md`

**...build a new API endpoint**
→ Read: `QUICK_REFERENCE.md` (5-step guide) + `API_DEVELOPMENT_GUIDE.md` (patterns)

**...test my API endpoints**
→ Read: `API_DEVELOPMENT_GUIDE.md` (Testing API section)

**...fix a database error**
→ Read: `DATABASE_AND_TROUBLESHOOTING.md` (Troubleshooting section)

**...understand the database schema**
→ Read: `DATABASE_AND_TROUBLESHOOTING.md` (Database Schema section)

**...implement authentication**
→ Read: `API_DEVELOPMENT_GUIDE.md` (Authentication section) + `AI_DEVELOPER_GUIDE.md` (Core Concepts)

**...add internationalization (I18n)**
→ Read: `AI_DEVELOPER_GUIDE.md` (Core Concepts section) + `API_DEVELOPMENT_GUIDE.md` (Example 2)

**...optimize database performance**
→ Read: `DATABASE_AND_TROUBLESHOOTING.md` (Performance Tips)

**...write common SQL queries**
→ Read: `DATABASE_AND_TROUBLESHOOTING.md` (Common Queries)

---

## 🎯 Key Files in Project

### Configuration
- `src/main/resources/application.properties` - Database, JWT, file paths
- `pom.xml` - Dependencies and build configuration

### Core Classes
- `SpringBootAnchoiApplication.java` - Application entry point
- `config/WebSecurityConfig.java` - Security configuration
- `config/BusinessException.java` - Custom exception
- `response/ResponseData.java` - API response wrapper
- `entity/BaseEntity.java` - Base class for all entities
- `security/jwt/JwtUtils.java` - JWT token operations

### Structure Pattern
```
entity/          → Database models
repository/      → Data access (JPA)
service/         → Business logic
controllers/     → REST endpoints
request/         → Request DTOs
response/        → Response DTOs
security/        → Authentication & JWT
config/          → Configuration classes
common/          → Utility functions
```

---

## 🚀 Getting Started in 10 Minutes

1. **Setup** (2 min)
   ```bash
   cd an_choi_viet_nam/backend
   mvn clean install -DskipTests
   ```

2. **Configure** (2 min)
   - Edit `application.properties` with your MySQL details

3. **Initialize Database** (2 min)
   - Create database: `CREATE DATABASE an_choi;`
   - Insert roles (see DATABASE_AND_TROUBLESHOOTING.md)

4. **Run** (1 min)
   ```bash
   mvn spring-boot:run
   ```

5. **Test** (3 min)
   - Get token: See QUICK_REFERENCE.md → "Login to Get Token"
   - Test endpoint: See API_DEVELOPMENT_GUIDE.md → "Using REST Client"

---

## 📚 Document Summary Table

| Document | Size | Purpose | Key Content | Read Time |
|----------|------|---------|-------------|-----------|
| QUICK_REFERENCE.md | ~560 lines | Quick lookup & getting started | Commands, patterns, checklist | 10 min |
| AI_DEVELOPER_GUIDE.md | ~750 lines | Deep architecture understanding | Full structure, core concepts, examples | 30 min |
| API_DEVELOPMENT_GUIDE.md | ~912 lines | API patterns & development | CRUD patterns, auth, testing, best practices | 40 min |
| DATABASE_AND_TROUBLESHOOTING.md | ~1080 lines | Database & problem-solving | Schema, troubleshooting, queries, performance | 45 min |
| DOCUMENTATION_INDEX.md | This file | Navigation & overview | Where to find what, key files | 5 min |

**Total Documentation**: ~3300 lines covering all aspects of backend development

---

## 🔑 Key Concepts Summary

### Layers
1. **Entity** (Database models extending BaseEntity)
2. **Repository** (Data access via JPA)
3. **Service** (Business logic and validation)
4. **Controller** (REST endpoints returning ResponseData)

### Security
- JWT tokens for stateless authentication
- @PreAuthorize for method-level authorization
- Roles: ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN

### I18n
- Base entity + EntityI18n pattern
- Language table for supported languages
- One translation per language

### Audit
- BaseEntity provides: id, createdDate, createdBy, updatedDate, updatedBy
- Auto-set via @PrePersist and @PreUpdate

### API Pattern
- ResponseData<T> wrapper for all responses
- BusinessException for error handling
- ModelMapper for entity ↔ DTO conversion

---

## ✅ Before Contributing Code

1. Read QUICK_REFERENCE.md - Pre-commit checklist
2. Follow patterns from API_DEVELOPMENT_GUIDE.md
3. Check database impacts in DATABASE_AND_TROUBLESHOOTING.md
4. Run: `mvn clean compile` to verify no errors
5. Test endpoints using REST Client examples

---

## 🆘 Troubleshooting Documentation Location

| Problem Type | See Document |
|--------------|--------------|
| Build errors | QUICK_REFERENCE.md → Common Errors |
| API endpoint not working | API_DEVELOPMENT_GUIDE.md → Error Handling |
| Database connection error | DATABASE_AND_TROUBLESHOOTING.md → Issues 1-2 |
| Data not saving | DATABASE_AND_TROUBLESHOOTING.md → Issues 3-4 |
| Performance issues | DATABASE_AND_TROUBLESHOOTING.md → Performance Tips |
| Authentication errors | API_DEVELOPMENT_GUIDE.md → Authentication section |
| Don't know where to start | QUICK_REFERENCE.md → Quick Reference |

---

## 📞 Quick Reference Links Within Docs

- **Setup**: AI_DEVELOPER_GUIDE.md → Setup & Installation
- **Architecture**: AI_DEVELOPER_GUIDE.md → Architecture section
- **CRUD Examples**: API_DEVELOPMENT_GUIDE.md → CRUD Operations Pattern
- **Testing**: API_DEVELOPMENT_GUIDE.md → Testing API
- **DB Schema**: DATABASE_AND_TROUBLESHOOTING.md → Database Schema
- **Common Queries**: DATABASE_AND_TROUBLESHOOTING.md → Common Queries
- **Code Examples**: QUICK_REFERENCE.md → Adding a New Feature (5 Steps)

---

## 🎓 Learning Path for New Developers

**Week 1**: Getting Started
- Day 1: Read QUICK_REFERENCE.md
- Day 2: Setup project & run tests from QUICK_REFERENCE.md
- Day 3: Read API_DEVELOPMENT_GUIDE.md Testing section
- Day 4: Test existing endpoints using provided examples
- Day 5: Read AI_DEVELOPER_GUIDE.md Architecture section

**Week 2**: Building Features
- Day 1-2: Read QUICK_REFERENCE.md "Adding a New Feature" section
- Day 3-4: Implement simple CRUD feature following the 5-step guide
- Day 5: Code review with senior developer

**Week 3+**: Advanced Topics
- Database optimization: DATABASE_AND_TROUBLESHOOTING.md → Performance Tips
- Complex queries: DATABASE_AND_TROUBLESHOOTING.md → Common Queries
- I18n features: AI_DEVELOPER_GUIDE.md → I18n section
- Security: API_DEVELOPMENT_GUIDE.md → Authentication section

---

## 📝 Last Updated
Documentation written for Spring Boot 2.7.3 backend
MySQL 5.7+ compatible
Java 8 (compiled to Java 11)

For latest updates, refer to README.md and pom.xml

---

**Happy coding! 🚀**
Start with QUICK_REFERENCE.md and feel free to deep dive into other guides as needed.