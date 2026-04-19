# 🔌 An Choi Việt Nam - API Development Patterns & Quick Reference

## Table of Contents
1. [API Endpoint Structure](#api-endpoint-structure)
2. [CRUD Operations Pattern](#crud-operations-pattern)
3. [Authentication & Authorization](#authentication--authorization)
4. [Request/Response Examples](#requestresponse-examples)
5. [Error Handling](#error-handling)
6. [Common Patterns](#common-patterns)
7. [Testing API](#testing-api)
8. [Best Practices](#best-practices)

---

## 🗂️ API Endpoint Structure

### URL Routing Convention
```
/api/{version}/{resource}/{id}/{action}
```

### Current API Routes
```
/api/auth/**              # Authentication endpoints
/api/admin/**             # Admin management endpoints
/api/app/**               # App user endpoints
/api/test/**              # Public test endpoints
/api/{resource}           # Resource endpoints (public GET, auth POST/PUT/DELETE)
/upload/**                # File upload endpoints
```

### Endpoint Examples
```
GET    /api/province                    # List all provinces
POST   /api/admin/province              # Create province (admin only)
GET    /api/province/{id}               # Get province by ID
PUT    /api/admin/province/{id}         # Update province
DELETE /api/admin/province/{id}         # Delete province

GET    /api/category                    # List categories
POST   /api/admin/category              # Create category
GET    /api/category/{id}               # Get category
PUT    /api/admin/category/{id}         # Update category
DELETE /api/admin/category/{id}         # Delete category

POST   /api/auth/signin                 # User login
POST   /api/auth/signup                 # User registration
POST   /api/auth/signout                # User logout
POST   /api/user/change-password        # Change password
```

---

## 🔄 CRUD Operations Pattern

### 1. CREATE (POST)
```
Endpoint: POST /api/admin/{resource}
Auth: Required (ROLE_ADMIN)
Request Body: {resource}Request DTO
Response: ResponseData<{resource}Response>

Example: Create Province
```

**Request:**
```bash
curl -X POST http://localhost:8888/api/admin/province \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "HN",
    "name": "Hà Nội"
  }'
```

**Response:**
```json
{
  "message": "OK",
  "status": 200,
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "code": "HN",
    "name": "Hà Nội",
    "createdDate": "2024-01-15 10:30:45"
  }
}
```

### 2. READ (GET)

**Get All:**
```bash
curl -X GET http://localhost:8888/api/province \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "OK",
  "status": 200,
  "data": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "code": "HN",
      "name": "Hà Nội",
      "createdDate": "2024-01-15 10:30:45"
    },
    {
      "id": "550e8400-e29b-41d4-a716-446655440001",
      "code": "HCM",
      "name": "TP. Hồ Chí Minh",
      "createdDate": "2024-01-15 10:31:00"
    }
  ]
}
```

**Get By ID:**
```bash
curl -X GET http://localhost:8888/api/province/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "OK",
  "status": 200,
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "code": "HN",
    "name": "Hà Nội",
    "districts": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440010",
        "code": "HBH",
        "name": "Hoàn Bà Chủ"
      }
    ],
    "createdDate": "2024-01-15 10:30:45"
  }
}
```

### 3. UPDATE (PUT)

**Request:**
```bash
curl -X PUT http://localhost:8888/api/admin/province/550e8400-e29b-41d4-a716-446655440000 \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "code": "HN",
    "name": "Hà Nội (Updated)"
  }'
```

**Response:**
```json
{
  "message": "OK",
  "status": 200,
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "code": "HN",
    "name": "Hà Nội (Updated)",
    "updatedDate": "2024-01-15 11:45:30"
  }
}
```

### 4. DELETE (DELETE)

**Request:**
```bash
curl -X DELETE http://localhost:8888/api/admin/province/550e8400-e29b-41d4-a716-446655440000 \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json"
```

**Response (Success):**
```json
{
  "message": "Deleted successfully",
  "status": 200,
  "data": null
}
```

---

## 🔐 Authentication & Authorization

### 1. User Login

**Request:**
```bash
curl -X POST http://localhost:8888/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Response:**
```json
{
  "message": "OK",
  "status": 200,
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "username": "admin",
    "email": "admin@example.com",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiYWRtaW4iLCJyb2xlT2JqZWN0IjpbIlJPTEVfQURNSU4iXSwicm9sZUFjdG9yIjpbIkFETUlOIl0sInN1YiI6ImFkbWluIiwiaWF0IjoxNjczNzc2MjQ1LCJleHAiOjE2NzM3ODI0NDV9.signature...",
    "roles": ["ROLE_ADMIN"]
  }
}
```

### 2. Using Token in Requests

**Add Authorization Header:**
```bash
curl -X GET http://localhost:8888/api/admin/user \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiYWRtaW4i..." \
  -H "Content-Type: application/json"
```

### 3. Role-Based Access Control

**Security Rules (in WebSecurityConfig):**
```
/api/auth/**          → permitAll (public)
/api/test/**          → permitAll (public)
GET requests          → permitAll (public)
/upload/**            → permitAll (public file access)
Other endpoints       → requiresAuthentication
```

**Method-level Authorization:**
```java
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<?> deleteUser(@PathVariable String id) {
    // Only admin can call this
}

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
public ResponseEntity<?> updateUser(@PathVariable String id) {
    // Admin and moderator can call
}

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public ResponseEntity<?> getProfile() {
    // Any authenticated user
}
```

### 4. User Registration

**Request:**
```bash
curl -X POST http://localhost:8888/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "pass123",
    "roles": ["user"]
  }'
```

**Response:**
```json
{
  "message": "User registered successfully",
  "status": 200,
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440050",
    "username": "newuser",
    "email": "newuser@example.com",
    "roles": ["ROLE_USER"]
  }
}
```

---

## 📨 Request/Response Examples

### Example 1: Get Items with Translations

**Request:**
```bash
curl -X GET "http://localhost:8888/api/item?languageId=en" \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "OK",
  "status": 200,
  "data": [
    {
      "id": "item-001",
      "name": "Game Name",
      "description": "Game Description",
      "category": {
        "id": "cat-001",
        "name": "Category Name"
      },
      "translations": [
        {
          "languageId": "en",
          "name": "Game Name",
          "description": "English description"
        },
        {
          "languageId": "vi",
          "name": "Tên Trò Chơi",
          "description": "Mô tả Tiếng Việt"
        }
      ]
    }
  ]
}
```

### Example 2: Create Item with I18n

**Request:**
```bash
curl -X POST http://localhost:8888/api/admin/item \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Puzzle Game",
    "categoryId": "cat-001",
    "translations": [
      {
        "languageId": "en",
        "name": "Puzzle Game",
        "description": "A fun puzzle game"
      },
      {
        "languageId": "vi",
        "name": "Trò Chơi Xếp Hình",
        "description": "Trò chơi xếp hình vui nhộn"
      }
    ]
  }'
```

### Example 3: File Upload

**Request:**
```bash
curl -X POST http://localhost:8888/upload/image \
  -H "Authorization: Bearer {token}" \
  -F "file=@/path/to/image.jpg"
```

**Response:**
```json
{
  "message": "File uploaded successfully",
  "status": 200,
  "data": {
    "fileName": "image-550e8400.jpg",
    "fileUrl": "/upload/image-550e8400.jpg",
    "fileSize": 256000,
    "mimeType": "image/jpeg"
  }
}
```

### Example 4: Search Functionality

**Request:**
```bash
curl -X GET "http://localhost:8888/api/search?query=puzzle&type=item&limit=10" \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "OK",
  "status": 200,
  "data": {
    "query": "puzzle",
    "total": 5,
    "results": [
      {
        "id": "item-001",
        "type": "item",
        "name": "Puzzle Game",
        "score": 0.95
      }
    ]
  }
}
```

---

## ⚠️ Error Handling

### 1. Error Response Format

```json
{
  "message": "Error description",
  "status": 400,
  "data": null
}
```

### 2. Common Error Codes & Messages

| Status | Message | Cause |
|--------|---------|-------|
| 400 | Bad Request | Invalid input data |
| 400 | User not found | User ID doesn't exist |
| 400 | Validation error | Input validation failed |
| 401 | Unauthorized | Missing/invalid JWT token |
| 403 | Forbidden | User doesn't have required role |
| 404 | Not Found | Resource doesn't exist |
| 500 | Internal Server Error | Unexpected server error |

### 3. Validation Error Example

**Request (invalid email):**
```bash
curl -X POST http://localhost:8888/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "email": "invalid-email",
    "password": "pass"
  }'
```

**Response:**
```json
{
  "message": "Email must be valid",
  "status": 400,
  "data": null
}
```

### 4. Authentication Error Example

**Request (missing token):**
```bash
curl -X GET http://localhost:8888/api/admin/user
```

**Response:**
```json
{
  "message": "Unauthorized",
  "status": 401,
  "data": null
}
```

### 5. Authorization Error Example

**Request (user tries to access admin endpoint):**
```bash
curl -X DELETE http://localhost:8888/api/admin/user/123 \
  -H "Authorization: Bearer {user_token}"
```

**Response:**
```json
{
  "message": "Access Denied",
  "status": 403,
  "data": null
}
```

---

## 🎯 Common Patterns

### Pattern 1: Get Single Resource with Related Data

```
Endpoint: GET /api/{resource}/{id}
Returns: Resource with all relationships and translations
Auth: None (public read)
```

**Example Structure:**
```json
{
  "id": "resource-id",
  "name": "Resource Name",
  "children": [ { ... }, { ... } ],
  "translations": [ { ... }, { ... } ],
  "metadata": { ... }
}
```

### Pattern 2: List with Pagination & Filtering

```
Endpoint: GET /api/{resource}?page=0&size=20&sort=name&filter=value
Returns: List of resources with pagination info
Auth: None (public read)
```

**Example:**
```bash
curl "http://localhost:8888/api/item?page=0&size=20&sort=name,desc"
```

**Response:**
```json
{
  "message": "OK",
  "status": 200,
  "data": {
    "content": [ { ... }, { ... } ],
    "pageNumber": 0,
    "pageSize": 20,
    "totalElements": 150,
    "totalPages": 8
  }
}
```

### Pattern 3: Update with Partial Data

```
Endpoint: PUT /api/admin/{resource}/{id}
Request: Only changed fields (id is required)
Response: Updated resource
Auth: ROLE_ADMIN
```

**Request (only updating name):**
```bash
curl -X PUT http://localhost:8888/api/admin/item/item-001 \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "item-001",
    "name": "Updated Name"
  }'
```

### Pattern 4: Bulk Operations

```
Endpoint: POST /api/admin/batch/{operation}
Request: Array of resources
Response: Results for each operation
Auth: ROLE_ADMIN
```

**Request (bulk delete):**
```bash
curl -X POST http://localhost:8888/api/admin/batch/delete \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "ids": ["id-1", "id-2", "id-3"]
  }'
```

### Pattern 5: Async Operations

```
Endpoint: POST /api/admin/{resource}/process
Request: Configuration for async task
Response: Task ID for polling
Auth: ROLE_ADMIN
```

**Request:**
```bash
curl -X POST http://localhost:8888/api/admin/video/process \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "fileId": "file-001",
    "format": "mp4",
    "quality": "high"
  }'
```

**Response:**
```json
{
  "message": "Processing started",
  "status": 200,
  "data": {
    "taskId": "task-550e8400",
    "status": "processing",
    "progress": 0,
    "estimatedTime": "5 minutes"
  }
}
```

---

## 🧪 Testing API

### Using cURL

```bash
# 1. Get token
TOKEN=$(curl -s -X POST http://localhost:8888/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' \
  | jq -r '.data.token')

# 2. Use token in requests
curl -X GET http://localhost:8888/api/admin/user \
  -H "Authorization: Bearer $TOKEN"

# 3. Create resource
curl -X POST http://localhost:8888/api/admin/category \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"New Category"}'
```

### Using Postman

1. **Setup Environment Variables:**
   - `base_url`: http://localhost:8888
   - `token`: Leave blank initially

2. **Create Auth Request (POST):**
   - URL: `{{base_url}}/api/auth/signin`
   - Body (JSON):
     ```json
     {
       "username": "admin",
       "password": "admin123"
     }
     ```
   - Tests tab (set token):
     ```javascript
     var jsonData = pm.response.json();
     pm.environment.set("token", jsonData.data.token);
     ```

3. **Create Resource Request (POST):**
   - URL: `{{base_url}}/api/admin/category`
   - Headers: `Authorization: Bearer {{token}}`
   - Body: Your JSON data

4. **Get Resources (GET):**
   - URL: `{{base_url}}/api/category`
   - Headers: `Content-Type: application/json`

### Using REST Client (VS Code)

Create `test.http` file:

```http
@baseUrl = http://localhost:8888
@token = 

### 1. Login
POST {{baseUrl}}/api/auth/signin
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

### 2. Create Category
POST {{baseUrl}}/api/admin/category
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "name": "Adventure",
  "type": "game",
  "icon": "icon-url"
}

### 3. Get All Categories
GET {{baseUrl}}/api/category
Content-Type: application/json

### 4. Get Category by ID
GET {{baseUrl}}/api/category/550e8400-e29b-41d4-a716-446655440000
Content-Type: application/json

### 5. Update Category
PUT {{baseUrl}}/api/admin/category/550e8400-e29b-41d4-a716-446655440000
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Updated Adventure"
}

### 6. Delete Category
DELETE {{baseUrl}}/api/admin/category/550e8400-e29b-41d4-a716-446655440000
Authorization: Bearer {{token}}
Content-Type: application/json
```

---

## ✅ Best Practices

### 1. Request Validation

Always validate input in Request DTOs:
```java
@Data
public class ItemRequest {
    private String id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @Size(min = 10, max = 500, message = "Description must be 10-500 characters")
    private String description;
    
    @NotNull(message = "Category ID is required")
    private String categoryId;
    
    @Email(message = "Email must be valid")
    private String email;
}
```

### 2. Consistent Response Format

Always use `ResponseData` wrapper:
```java
@RestController
public class ItemController {
    
    @GetMapping
    public ResponseEntity<ResponseData<List<ItemResponse>>> getAll() {
        List<ItemResponse> items = itemService.getAll();
        return ResponseEntity.ok(ResponseData.ok(items));
    }
    
    @PostMapping
    public ResponseEntity<ResponseData<ItemResponse>> create(@Valid @RequestBody ItemRequest request) {
        ItemResponse response = itemService.create(request);
        return ResponseEntity.status(201).body(ResponseData.ok(response));
    }
}
```

### 3. Error Handling

Use BusinessException for business errors:
```java
public ItemResponse getById(String id) throws BusinessException {
    Item item = itemRepository.findById(id)
        .orElseThrow(() -> new BusinessException("404", "Item not found"));
    return modelMapper.map(item, ItemResponse.class);
}
```

### 4. JWT Token Management

- Keep tokens in secure storage (HttpOnly cookies or secure localStorage)
- Include token in Authorization header: `Authorization: Bearer <token>`
- Handle token expiration gracefully (refresh or re-login)
- Never expose token in logs or debug output

### 5. Pagination for Large Datasets

```java
@GetMapping
public ResponseEntity<?> list(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size) {
    
    Page<Item> items = itemRepository.findAll(
        PageRequest.of(page, size, Sort.by("createdDate").descending())
    );
    
    return ResponseEntity.ok(ResponseData.ok(items));
}
```

### 6. Proper HTTP Status Codes

```java
// GET - 200 OK
// POST - 201 Created
// PUT - 200 OK (or 204 No Content)
// DELETE - 204 No Content (or 200 OK)
// Invalid input - 400 Bad Request
// Unauthorized - 401 Unauthorized
// Forbidden - 403 Forbidden
// Not found - 404 Not Found
// Server error - 500 Internal Server Error
```

### 7. API Versioning (if needed)

```java
@RestController
@RequestMapping("/api/v1/item")
public class ItemControllerV1 { ... }

@RestController
@RequestMapping("/api/v2/item")
public class ItemControllerV2 { ... }
```

### 8. Logging Important Operations

```java
@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    
    public ItemResponse create(ItemRequest request) {
        logger.info("Creating item: {}", request.getName());
        // ... create logic
        logger.info("Item created successfully: {}", item.getId());
        return response;
    }
}
```

### 9. Security Best Practices

```java
// ✅ Good - Password encoded in controller
@PostMapping("/signup")
public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    // Store encoded password
}

// ❌ Bad - Password in plain text
user.setPassword(request.getPassword());

// ✅ Good - Check authorization
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable String id) { ... }

// ❌ Bad - No authorization check
@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable String id) { ... }
```

### 10. API Documentation Comments

```java
/**
 * Retrieve all items with optional filtering and pagination
 *
 * @param page    Zero-indexed page number (default: 0)
 * @param size    Number of items per page (default: 20)
 * @param sort    Sort field and direction (e.g., "name,desc")
 * @return        Page of ItemResponse objects
 * @throws        No exceptions thrown; errors returned in response
 *
 * @example
 * GET /api/item?page=0&size=20&sort=name,asc
 */
@GetMapping
public ResponseEntity<ResponseData<Page<ItemResponse>>> list(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size,
    @RequestParam(defaultValue = "createdDate,desc") String sort) {
    // Implementation
}
```

---

## 📚 Quick Reference Checklist

When creating a new API endpoint:

- [ ] Create Request DTO with validations (@NotBlank, @Email, etc.)
- [ ] Create Response DTO
- [ ] Create/update Entity extending BaseEntity
- [ ] Create Repository extending CommonRepository
- [ ] Create Service with business logic
- [ ] Create Controller endpoint
- [ ] Add @PreAuthorize if authorization needed
- [ ] Handle BusinessException cases
- [ ] Test endpoint with cURL or Postman
- [ ] Document in comments
- [ ] Check for SQL injection / XSS vulnerabilities
- [ ] Ensure proper HTTP status codes
- [ ] Return ResponseData wrapper
- [ ] Add logging for debugging
- [ ] Test error scenarios
