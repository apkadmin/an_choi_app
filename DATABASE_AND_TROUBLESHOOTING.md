# 🗄️ An Choi Việt Nam - Database Schema & Troubleshooting Guide

## Table of Contents
1. [Database Schema](#database-schema)
2. [Entity Relationships](#entity-relationships)
3. [Database Initialization](#database-initialization)
4. [Troubleshooting Guide](#troubleshooting-guide)
5. [Performance Tips](#performance-tips)
6. [Common Queries](#common-queries)

---

## 🗂️ Database Schema

### Core Tables

#### 1. User Table
User accounts for authentication and authorization.

```sql
CREATE TABLE user (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    phone VARCHAR(20),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Key Fields:**
- `username`: Unique identifier for login
- `password`: BCrypt encoded password
- `email`: User contact email
- `created_date`: Account creation timestamp
- `updated_date`: Last modification timestamp

#### 2. RoleUser Table
Role assignment for users (Many-to-Many relationship with User).

```sql
CREATE TABLE role_user (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default roles
INSERT INTO role_user (id, name, created_date, created_by) 
VALUES 
    (UUID(), 'ROLE_USER', NOW(), 'system'),
    (UUID(), 'ROLE_MODERATOR', NOW(), 'system'),
    (UUID(), 'ROLE_ADMIN', NOW(), 'system');
```

#### 3. Category Table
Game categories/classifications.

```sql
CREATE TABLE category (
    id VARCHAR(36) PRIMARY KEY,
    icon VARCHAR(255),
    type VARCHAR(100),
    value VARCHAR(255),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 4. CategoryI18n Table
Translations for categories (supports multiple languages).

```sql
CREATE TABLE category_i18n (
    id VARCHAR(36) PRIMARY KEY,
    category_id VARCHAR(36) NOT NULL,
    language_id VARCHAR(10),
    name VARCHAR(255),
    description TEXT,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE,
    INDEX idx_category_id (category_id),
    INDEX idx_language_id (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 5. Province Table
Vietnamese provinces.

```sql
CREATE TABLE province (
    id VARCHAR(36) PRIMARY KEY,
    code VARCHAR(10),
    name VARCHAR(255),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    INDEX idx_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 6. ProvinceI18n Table
Province translations.

```sql
CREATE TABLE province_i18n (
    id VARCHAR(36) PRIMARY KEY,
    province_id VARCHAR(36) NOT NULL,
    language_id VARCHAR(10),
    name VARCHAR(255),
    description TEXT,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (province_id) REFERENCES province(id) ON DELETE CASCADE,
    INDEX idx_province_id (province_id),
    INDEX idx_language_id (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 7. District Table
Districts within provinces.

```sql
CREATE TABLE district (
    id VARCHAR(36) PRIMARY KEY,
    province_id VARCHAR(36),
    code VARCHAR(10),
    name VARCHAR(255),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (province_id) REFERENCES province(id) ON DELETE CASCADE,
    INDEX idx_province_id (province_id),
    INDEX idx_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 8. DistrictI18n Table
District translations.

```sql
CREATE TABLE district_i18n (
    id VARCHAR(36) PRIMARY KEY,
    district_id VARCHAR(36) NOT NULL,
    language_id VARCHAR(10),
    name VARCHAR(255),
    description TEXT,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (district_id) REFERENCES district(id) ON DELETE CASCADE,
    INDEX idx_district_id (district_id),
    INDEX idx_language_id (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 9. Item Table
Game items/games.

```sql
CREATE TABLE item (
    id VARCHAR(36) PRIMARY KEY,
    category_id VARCHAR(36),
    name VARCHAR(255),
    description TEXT,
    image_url VARCHAR(500),
    status VARCHAR(50),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL,
    INDEX idx_category_id (category_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 10. ItemI18n Table
Item/Game translations.

```sql
CREATE TABLE item_i18n (
    id VARCHAR(36) PRIMARY KEY,
    item_id VARCHAR(36) NOT NULL,
    language_id VARCHAR(10),
    name VARCHAR(255),
    description TEXT,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE,
    INDEX idx_item_id (item_id),
    INDEX idx_language_id (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 11. Media Table
Media files (images, videos).

```sql
CREATE TABLE media (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    file_name VARCHAR(500),
    file_url VARCHAR(500),
    file_type VARCHAR(50),
    file_size BIGINT,
    mime_type VARCHAR(100),
    status VARCHAR(50),
    duration INT COMMENT 'Video duration in seconds',
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    INDEX idx_file_type (file_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 12. MediaI18n Table
Media translations/descriptions.

```sql
CREATE TABLE media_i18n (
    id VARCHAR(36) PRIMARY KEY,
    media_id VARCHAR(36) NOT NULL,
    language_id VARCHAR(10),
    name VARCHAR(255),
    description TEXT,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE,
    INDEX idx_media_id (media_id),
    INDEX idx_language_id (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 13. Question Table
Quiz questions.

```sql
CREATE TABLE question (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    question_type VARCHAR(50),
    difficulty VARCHAR(50),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    INDEX idx_question_type (question_type),
    INDEX idx_difficulty (difficulty)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 14. QuestionDetail Table
Question answers/options.

```sql
CREATE TABLE question_detail (
    id VARCHAR(36) PRIMARY KEY,
    question_id VARCHAR(36) NOT NULL,
    answer_text TEXT,
    is_correct BOOLEAN,
    order_index INT,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE,
    INDEX idx_question_id (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 15. Post Table
Blog posts/news articles.

```sql
CREATE TABLE post (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    author VARCHAR(255),
    status VARCHAR(50),
    view_count INT DEFAULT 0,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    INDEX idx_status (status),
    INDEX idx_author (author)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 16. JigsawDataEntity Table
Jigsaw puzzle data.

```sql
CREATE TABLE jigsaw_data (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    image_url VARCHAR(500),
    pieces_count INT,
    difficulty VARCHAR(50),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    INDEX idx_difficulty (difficulty)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 17. JigsawDetailEntity Table
Jigsaw puzzle pieces.

```sql
CREATE TABLE jigsaw_detail (
    id VARCHAR(36) PRIMARY KEY,
    jigsaw_id VARCHAR(36) NOT NULL,
    piece_index INT,
    position_x INT,
    position_y INT,
    image_url VARCHAR(500),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (jigsaw_id) REFERENCES jigsaw_data(id) ON DELETE CASCADE,
    INDEX idx_jigsaw_id (jigsaw_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 18. GameUser Table
User progress in games.

```sql
CREATE TABLE game_user (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36),
    item_id VARCHAR(36),
    score INT DEFAULT 0,
    level INT DEFAULT 1,
    progress_percentage INT DEFAULT 0,
    last_played_date DATETIME,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_item (user_id, item_id),
    INDEX idx_user_id (user_id),
    INDEX idx_item_id (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 19. PointVietnamEntity Table
Vietnam location points/landmarks.

```sql
CREATE TABLE point_vietnam (
    id VARCHAR(36) PRIMARY KEY,
    province_id VARCHAR(36),
    district_id VARCHAR(36),
    name VARCHAR(255),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    description TEXT,
    image_url VARCHAR(500),
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    FOREIGN KEY (province_id) REFERENCES province(id),
    FOREIGN KEY (district_id) REFERENCES district(id),
    INDEX idx_province_id (province_id),
    INDEX idx_district_id (district_id),
    SPATIAL INDEX sp_idx_location (latitude, longitude)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### 20. Language Table
Supported languages.

```sql
CREATE TABLE language (
    id VARCHAR(36) PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_date DATETIME,
    created_by VARCHAR(255),
    updated_date DATETIME,
    updated_by VARCHAR(255),
    INDEX idx_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default languages
INSERT INTO language (id, code, name, is_active, created_date, created_by) VALUES
    (UUID(), 'vi', 'Tiếng Việt', TRUE, NOW(), 'system'),
    (UUID(), 'en', 'English', TRUE, NOW(), 'system');
```

---

## 🔗 Entity Relationships

### Relationship Diagram

```
User (1) ─────────────────────────────── (many) RoleUser
         assigned roles

User (1) ─────────────────────────────── (many) GameUser
         plays games                      tracks progress

Category (1) ──────────────────────────── (many) Item
             categorizes                  belongs to

Item (1) ───────────────────────────────── (many) GameUser
         contains                          user progress

Item (1) ───────────────────────────────── (many) ItemI18n
         has translations

Province (1) ────────────────────────────── (many) District
             contains

Province (1) ────────────────────────────── (many) PointVietnam
             contains landmarks

District (1) ────────────────────────────── (many) PointVietnam
             contains landmarks

Question (1) ────────────────────────────── (many) QuestionDetail
             has answers/options

JigsawData (1) ────────────────────────── (many) JigsawDetail
               composed of pieces

Media (1) ───────────────────────────────── (many) MediaI18n
         has translations

Language (supporting table for all I18n)
```

### Key Relationships by Entity

**User → RoleUser (Many-to-Many)**
- User can have multiple roles
- Role can be assigned to multiple users
- Junction table: user_role (not shown in separate tables above)

**Category → Item (One-to-Many)**
- One category has multiple items
- Item.category_id → Category.id

**Province → District (One-to-Many)**
- One province has multiple districts
- District.province_id → Province.id

**Item → GameUser (One-to-Many)**
- One item has many user progress records
- GameUser.item_id → Item.id

**Question → QuestionDetail (One-to-Many)**
- One question has multiple answer options
- QuestionDetail.question_id → Question.id

**All → I18n (One-to-Many)**
- Each entity with translations has corresponding I18n table
- Pattern: Entity (1) → EntityI18n (many)

---

## 💾 Database Initialization

### Complete SQL Setup Script

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS an_choi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE an_choi;

-- Create all tables
-- (See schema section above)

-- Insert default data
INSERT INTO language (id, code, name, is_active, created_date, created_by) 
VALUES 
    (UUID(), 'vi', 'Tiếng Việt', TRUE, NOW(), 'system'),
    (UUID(), 'en', 'English', TRUE, NOW(), 'system');

INSERT INTO role_user (id, name, created_date, created_by) 
VALUES 
    (UUID(), 'ROLE_USER', NOW(), 'system'),
    (UUID(), 'ROLE_MODERATOR', NOW(), 'system'),
    (UUID(), 'ROLE_ADMIN', NOW(), 'system');

-- Create indexes for better performance
CREATE INDEX idx_user_username ON user(username);
CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_item_status ON item(status);
CREATE INDEX idx_media_status ON media(status);
CREATE INDEX idx_post_status ON post(status);
CREATE INDEX idx_gameuser_user_item ON game_user(user_id, item_id);
```

### Using MySQL Workbench

1. Open MySQL Workbench
2. Create new connection to your MySQL server
3. Right-click on Schemas → Create Schema
4. Name: `an_choi`
5. Charset: utf8mb4
6. Collation: utf8mb4_unicode_ci
7. Apply
8. Open Query Editor
9. Copy and paste the SQL setup script above
10. Execute (Ctrl+Enter or Cmd+Enter)

### Using Command Line

```bash
# Connect to MySQL
mysql -u root -p

# Create database
CREATE DATABASE an_choi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Use database
USE an_choi;

# Source SQL file (if you have setup.sql)
SOURCE /path/to/setup.sql;

# Verify tables created
SHOW TABLES;
```

### With Spring Boot (Automatic)

Spring Boot can auto-create tables using Hibernate with `spring.jpa.hibernate.ddl-auto=create` or `update` in `application.properties`.

However, it's recommended to:
1. Set `ddl-auto=validate` in production (don't auto-create)
2. Use Flyway or Liquibase for migrations (not currently setup)
3. Manage schema manually with SQL scripts

---

## 🔧 Troubleshooting Guide

### Issue 1: "Unknown database 'an_choi'"

**Error:**
```
com.mysql.cj.jdbc.exceptions.SQLNonTransientConnectionException: 
Unknown database 'an_choi'
```

**Causes:**
- Database doesn't exist
- Wrong database name in connection string
- MySQL server not running

**Solution:**
```sql
-- Create database if not exists
CREATE DATABASE an_choi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Or verify in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/an_choi?...
```

### Issue 2: "Access denied for user"

**Error:**
```
com.mysql.cj.jdbc.exceptions.SQLNonTransientConnectionException: 
Access denied for user 'username'@'hostname'
```

**Causes:**
- Wrong username/password in connection string
- User doesn't have permissions for database
- User account doesn't exist

**Solution:**
```sql
-- Create user (on MySQL server)
CREATE USER 'anchoi12$1'@'%' IDENTIFIED BY '3K4xMdb@59ul';

-- Grant permissions
GRANT ALL PRIVILEGES ON an_choi.* TO 'anchoi12$1'@'%';

-- Or if connecting from specific host
CREATE USER 'anchoi12$1'@'localhost' IDENTIFIED BY '3K4xMdb@59ul';
GRANT ALL PRIVILEGES ON an_choi.* TO 'anchoi12$1'@'localhost';

-- Refresh privileges
FLUSH PRIVILEGES;
```

### Issue 3: "Table doesn't exist"

**Error:**
```
com.mysql.cj.jdbc.exceptions.SQLSyntaxErrorException: 
Table 'an_choi.item' doesn't exist
```

**Causes:**
- Hibernate DDL-auto setting is `validate` but tables don't exist
- Tables were deleted
- Wrong table name in entity mapping

**Solution:**
```sql
-- Check existing tables
SHOW TABLES;

-- Check entity @Table annotation
@Entity
@Table(name = "item")  // Table name must match

-- Recreate tables (if safe to delete)
DROP TABLE IF EXISTS item;

-- Let Hibernate recreate (set ddl-auto=create or update)
spring.jpa.hibernate.ddl-auto=update
```

### Issue 4: "Duplicate entry" on unique constraint

**Error:**
```
java.sql.SQLIntegrityConstraintViolationException: 
Duplicate entry 'username' for key 'username'
```

**Causes:**
- Trying to insert duplicate value in unique column
- @UniqueConstraint on entity field

**Solution:**
```sql
-- Check existing data
SELECT username FROM user WHERE username = 'admin';

-- Use unique ID/UUID if possible
-- Or check for duplicates before insert
-- Implement uniqueness validation in service

@Service
public class UserService {
    public void create(UserRequest request) throws BusinessException {
        // Check uniqueness before saving
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BusinessException("400", "Username already exists");
        }
    }
}
```

### Issue 5: "Foreign key constraint fails"

**Error:**
```
java.sql.SQLIntegrityConstraintViolationException: 
Cannot add or update a child row: a foreign key constraint fails
```

**Causes:**
- Referenced parent record doesn't exist
- Deleting parent while children reference it
- Wrong ID type or format

**Solution:**
```sql
-- Check parent exists before inserting child
SELECT * FROM category WHERE id = '550e8400...';

-- Delete children before parent (or use CASCADE)
DELETE FROM item WHERE category_id = '550e8400...';
DELETE FROM category WHERE id = '550e8400...';

-- Or set CASCADE in table definition
FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
```

### Issue 6: "Data too long for column"

**Error:**
```
java.sql.SQLDataException: 
Data too long for column 'name' at row 1
```

**Causes:**
- Inserting string longer than VARCHAR limit
- VARCHAR(50) but string is 100 characters

**Solution:**
```sql
-- Check column definition
DESC item;
-- Shows column sizes

-- Update column size if needed
ALTER TABLE item MODIFY name VARCHAR(500);

-- Or validate input length in request DTO
@Data
public class ItemRequest {
    @Size(max = 50, message = "Name max 50 characters")
    private String name;
}
```

### Issue 7: "No suitable driver found"

**Error:**
```
java.sql.SQLException: 
No suitable driver found for jdbc:mysql://localhost:3306/an_choi
```

**Causes:**
- MySQL connector JAR not in classpath
- Maven dependency not downloaded
- Wrong JDBC URL format

**Solution:**
```xml
<!-- Add to pom.xml -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Run Maven -->
mvn clean install
```

### Issue 8: "Connection refused"

**Error:**
```
java.sql.SQLException: 
Could not connect to address=(host=localhost)(port=3306): 
Connection refused
```

**Causes:**
- MySQL server not running
- Wrong host/port
- Firewall blocking connection

**Solution:**
```bash
# Start MySQL (macOS)
brew services start mysql
# or
mysql.server start

# Start MySQL (Linux)
sudo systemctl start mysql

# Start MySQL (Windows)
net start MySQL80

# Test connection
mysql -u root -p -h localhost -P 3306

# Check if server listening
netstat -an | grep 3306
```

### Issue 9: "Hibernate mapping error"

**Error:**
```
org.hibernate.AnnotationException: 
Unable to determine SQL type for class
```

**Causes:**
- Using non-serializable type in entity
- Column without @Column annotation
- Unsupported type for database

**Solution:**
```java
// ✅ Good - With proper annotation
@Entity
public class Item extends BaseEntity {
    @Column(name = "name", length = 255)
    private String name;
    
    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
}

// ❌ Bad - Missing annotation
@Entity
public class Item extends BaseEntity {
    private String name;  // No @Column
}
```

### Issue 10: "Lazy initialization exception"

**Error:**
```
org.hibernate.LazyInitializationException: 
failed to lazily initialize a collection
```

**Causes:**
- Accessing lazy-loaded relationship outside transaction
- Session closed after query

**Solution:**
```java
// Use JOIN FETCH to load relationships
@Query("SELECT i FROM Item i JOIN FETCH i.translations WHERE i.id = :id")
Optional<Item> findByIdWithTranslations(@Param("id") String id);

// Or change fetch type
@Entity
public class Item extends BaseEntity {
    @OneToMany(mappedBy = "itemId", fetch = FetchType.EAGER)
    List<ItemI18n> translations;
}

// Or use @Transactional
@Service
@Transactional
public class ItemService {
    public ItemResponse getById(String id) {
        Item item = itemRepository.findById(id).orElseThrow();
        // Safe to access translations here
        return modelMapper.map(item, ItemResponse.class);
    }
}
```

---

## ⚡ Performance Tips

### 1. Add Indexes for Frequently Queried Columns

```sql
-- User login queries
CREATE INDEX idx_user_username ON user(username);
CREATE INDEX idx_user_email ON user(email);

-- Category lookups
CREATE INDEX idx_category_type ON category(type);

-- Item searches
CREATE INDEX idx_item_category_id ON item(category_id);
CREATE INDEX idx_item_status ON item(status);

-- Game progress
CREATE INDEX idx_gameuser_user ON game_user(user_id);
CREATE INDEX idx_gameuser_item ON game_user(item_id);

-- Location searches
CREATE SPATIAL INDEX sp_idx_point_location ON point_vietnam(latitude, longitude);
```

### 2. Use Pagination for Large Result Sets

```java
@GetMapping
public ResponseEntity<ResponseData<Page<ItemResponse>>> list(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size) {
    
    Page<Item> items = itemRepository.findAll(
        PageRequest.of(page, size, Sort.by("createdDate").descending())
    );
    
    return ResponseEntity.ok(ResponseData.ok(items));
}
```

### 3. Use JOIN FETCH to Avoid N+1 Queries

```java
// ❌ Bad - N+1 query problem
List<Item> items = itemRepository.findAll();
items.forEach(item -> item.getTranslations());  // Extra query for each item

// ✅ Good - Single query with JOIN FETCH
@Query("SELECT DISTINCT i FROM Item i JOIN FETCH i.translations")
List<Item> findAllWithTranslations();
```

### 4. Cache Static Data

```java
@Service
@CacheConfig(cacheNames = "languages")
public class LanguageService {
    
    @Cacheable(value = "languages")
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
    
    @CacheEvict(value = "languages", allEntries = true)
    public Language create(Language language) {
        return languageRepository.save(language);
    }
}
```

### 5. Use Projection for Specific Fields

```java
// Return only needed fields, not whole entity
public interface ItemNameOnlyProjection {
    String getId();
    String getName();
}

@Repository
public interface ItemRepository extends CommonRepository<Item> {
    List<ItemNameOnlyProjection> findAllProjectedBy();
}
```

---

## 📝 Common Queries

### User Queries

```sql
-- Get user with roles
SELECT u.*, r.name as role 
FROM user u
LEFT JOIN user_role ur ON u.id = ur.user_id
LEFT JOIN role_user r ON ur.role_id = r.id
WHERE u.username = 'admin';

-- Find users created in last 7 days
SELECT * FROM user 
WHERE created_date >= DATE_SUB(NOW(), INTERVAL 7 DAY)
ORDER BY created_date DESC;

-- Count users by role
SELECT r.name, COUNT(u.id) as user_count
FROM role_user r
LEFT JOIN user_role ur ON r.id = ur.role_id
LEFT JOIN user u ON ur.user_id = u.id
GROUP BY r.name;
```

### Item Queries

```sql
-- Get items with translations
SELECT i.id, i.name, it.language_id, it.name as translated_name
FROM item i
LEFT JOIN item_i18n it ON i.id = it.item_id
WHERE i.status = 'ACTIVE'
ORDER BY i.created_date DESC;

-- Find items by category
SELECT i.* FROM item i
WHERE i.category_id = 'category-001'
AND i.status = 'ACTIVE';

-- Get popular items by play count
SELECT i.id, i.name, COUNT(gu.id) as play_count
FROM item i
LEFT JOIN game_user gu ON i.id = gu.item_id
GROUP BY i.id
ORDER BY play_count DESC
LIMIT 10;
```

### Location Queries

```sql
-- Get all districts in a province
SELECT d.* FROM district d
WHERE d.province_id = 'province-001'
ORDER BY d.name;

-- Get landmarks in a district
SELECT * FROM point_vietnam
WHERE district_id = 'district-001'
ORDER BY name;

-- Find nearby landmarks (within 5km)
SELECT *
FROM point_vietnam
WHERE ST_Distance_Sphere(
    POINT(latitude, longitude),
    POINT(21.0285, 105.8542)
) <= 5000;  -- 5000 meters
```

### Game Progress Queries

```sql
-- Get user game progress
SELECT g.*, i.name as item_name, u.username
FROM game_user g
LEFT JOIN item i ON g.item_id = i.id
LEFT JOIN user u ON g.user_id = u.id
WHERE g.user_id = 'user-001'
ORDER BY g.updated_date DESC;

-- Find users who played specific item
SELECT u.*, g.score, g.level
FROM user u
JOIN game_user g ON u.id = g.user_id
WHERE g.item_id = 'item-001'
ORDER BY g.score DESC;

-- Average score per item
SELECT i.name, AVG(g.score) as avg_score, COUNT(g.id) as play_count
FROM item i
LEFT JOIN game_user g ON i.id = g.item_id
GROUP BY i.id
HAVING play_count > 0
ORDER BY avg_score DESC;
```

### Content Queries

```sql
-- Get published posts with author
SELECT p.id, p.title, p.author, p.view_count, p.created_date
FROM post p
WHERE p.status = 'PUBLISHED'
ORDER BY p.created_date DESC
LIMIT 10;

-- Get questions by difficulty
SELECT q.id, q.title, q.difficulty, COUNT(qd.id) as answer_count
FROM question q
LEFT JOIN question_detail qd ON q.id = qd.question_id
WHERE q.difficulty = 'HARD'
GROUP BY q.id;

-- Get quiz with answers
SELECT q.*, qd.answer_text, qd.is_correct
FROM question q
LEFT JOIN question_detail qd ON q.id = qd.question_id
WHERE q.id = 'question-001'
ORDER BY qd.order_index;
```
