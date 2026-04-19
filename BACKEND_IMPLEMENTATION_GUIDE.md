# 🎯 Backend Implementation Guide - Hints & Audio Feature

## Overview
This guide provides complete backend implementation for the Hints & Audio feature.

---

## ✅ Part 1: Entities (COMPLETED)

### Game.java ✅
Created at: `backend/src/main/java/com/anchoi/entity/Game.java`

```java
package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "game")
public class Game extends BaseEntity {

    @Basic
    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image;

    @Basic
    @Column(name = "type")
    private Integer type;

    @Basic
    @Column(name = "name", length = 255)
    private String name;

    @Basic
    @Column(name = "max_diamonds_allowed")
    private Integer maxDiamondsAllowed;

    @Basic
    @Column(name = "enable_hints")
    private Boolean enableHints;

    @Basic
    @Column(name = "hint_config", columnDefinition = "JSON")
    private String hintConfig;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameDetail> dataDetails;
}
```

### GameDetail.java ✅
Created at: `backend/src/main/java/com/anchoi/entity/GameDetail.java`

```java
package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "game_detail")
public class GameDetail extends BaseEntity {

    @Basic
    @Column(name = "game_id")
    private String gameId;

    @Basic
    @Column(name = "x")
    private Double x;

    @Basic
    @Column(name = "y")
    private Double y;

    @Basic
    @Column(name = "width")
    private Double width;

    @Basic
    @Column(name = "height")
    private Double height;

    @Basic
    @Column(name = "d", columnDefinition = "LONGTEXT")
    private String d;

    @Basic
    @Column(name = "color")
    private String color;

    @Basic
    @Column(name = "max_diamonds")
    private Integer maxDiamonds;

    @OneToMany(mappedBy = "gameDetail", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameHint> hints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;
}
```

### GameHint.java ✅
Created at: `backend/src/main/java/com/anchoi/entity/GameHint.java`

```java
package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "game_hint", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"game_detail_id", "level"})
})
public class GameHint extends BaseEntity {

    @Basic
    @Column(name = "game_detail_id")
    private String gameDetailId;

    @Basic
    @Column(name = "level", nullable = false)
    private Integer level;

    @Basic
    @Column(name = "text", columnDefinition = "LONGTEXT")
    private String text;

    @Basic
    @Column(name = "audio_url", columnDefinition = "LONGTEXT")
    private String audioUrl;

    @Basic
    @Column(name = "audio_file_name", length = 255)
    private String audioFileName;

    @Basic
    @Column(name = "point_deduction")
    private Integer pointDeduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_detail_id", insertable = false, updatable = false)
    private GameDetail gameDetail;
}
```

---

## ✅ Part 2: Repositories (COMPLETED)

### GameRepository.java ✅
Created at: `backend/src/main/java/com/anchoi/repository/game/GameRepository.java`

```java
package com.anchoi.repository.game;

import com.anchoi.entity.Game;
import com.anchoi.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CommonRepository<Game> {

}
```

### GameDetailRepository.java ✅
Created at: `backend/src/main/java/com/anchoi/repository/game/GameDetailRepository.java`

```java
package com.anchoi.repository.game;

import com.anchoi.entity.GameDetail;
import com.anchoi.repository.CommonRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameDetailRepository extends CommonRepository<GameDetail> {
    List<GameDetail> findByGameId(String gameId);
}
```

### GameHintRepository.java ✅
Created at: `backend/src/main/java/com/anchoi/repository/game/GameHintRepository.java`

```java
package com.anchoi.repository.game;

import com.anchoi.entity.GameHint;
import com.anchoi.repository.CommonRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameHintRepository extends CommonRepository<GameHint> {
    List<GameHint> findByGameDetailId(String gameDetailId);
}
```

---

## ⏳ Part 3: Request DTOs (TO CREATE)

### HintRequest.java ✅
Create at: `backend/src/main/java/com/anchoi/request/HintRequest.java`

```java
package com.anchoi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HintRequest {
    private Integer level;
    private String text;
    private String audioUrl;
    private String audioFileName;
    private Integer pointDeduction;
}
```

### GameDetailRequest.java ✅
Create at: `backend/src/main/java/com/anchoi/request/GameDetailRequest.java`

```java
package com.anchoi.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDetailRequest {
    private String id;
    private String gameId;
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private String d;
    private String color;
    private Integer maxDiamonds;
    private List<HintRequest> hints;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;
}
```

### GameRequest.java
Create at: `backend/src/main/java/com/anchoi/request/GameRequest.java`

```java
package com.anchoi.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {
    private String id;
    private String image;
    private Integer type;
    private String name;
    private Integer maxDiamondsAllowed;
    private Boolean enableHints;
    private Map<String, Object> hintConfiguration;
    private List<GameDetailRequest> dataDetails;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;
}
```

---

## ⏳ Part 4: Response DTOs (TO CREATE)

### HintResponse.java
Create at: `backend/src/main/java/com/anchoi/response/HintResponse.java`

```java
package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HintResponse {
    private String id;
    private Integer level;
    private String text;
    private String audioUrl;
    private String audioFileName;
    private Integer pointDeduction;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private Date updatedDate;
}
```

### GameDetailResponse.java
Create at: `backend/src/main/java/com/anchoi/response/GameDetailResponse.java`

```java
package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDetailResponse {
    private String id;
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private String d;
    private String color;
    private Integer maxDiamonds;
    private List<HintResponse> hints;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private Date updatedDate;
}
```

### GameResponse.java
Create at: `backend/src/main/java/com/anchoi/response/GameResponse.java`

```java
package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private String id;
    private String image;
    private Integer type;
    private String name;
    private Integer maxDiamondsAllowed;
    private Boolean enableHints;
    private Map<String, Object> hintConfiguration;
    private List<GameDetailResponse> dataDetails;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private Date updatedDate;
}
```

---

## ⏳ Part 5: Service (TO CREATE)

### GameService.java
Create at: `backend/src/main/java/com/anchoi/service/GameService.java`

```java
package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.Game;
import com.anchoi.entity.GameDetail;
import com.anchoi.entity.GameHint;
import com.anchoi.repository.game.GameRepository;
import com.anchoi.repository.game.GameDetailRepository;
import com.anchoi.repository.game.GameHintRepository;
import com.anchoi.request.GameRequest;
import com.anchoi.request.GameDetailRequest;
import com.anchoi.request.HintRequest;
import com.anchoi.response.GameResponse;
import com.anchoi.response.GameDetailResponse;
import com.anchoi.response.HintResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    
    private final GameRepository gameRepository;
    private final GameDetailRepository gameDetailRepository;
    private final GameHintRepository gameHintRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    
    /**
     * Create or update game with details and hints
     */
    public GameResponse save(GameRequest request) throws BusinessException {
        try {
            Game game = new Game();
            
            // If ID exists, load existing game for update
            if (request.getId() != null && !request.getId().isEmpty()) {
                game = gameRepository.findById(request.getId())
                        .orElseThrow(() -> new BusinessException("Game not found"));
            }
            
            game.setImage(request.getImage());
            game.setType(request.getType());
            game.setName(request.getName());
            game.setMaxDiamondsAllowed(request.getMaxDiamondsAllowed());
            game.setEnableHints(request.getEnableHints() != null ? request.getEnableHints() : true);
            
            // Save hint configuration as JSON
            if (request.getHintConfiguration() != null) {
                try {
                    game.setHintConfig(objectMapper.writeValueAsString(request.getHintConfiguration()));
                } catch (Exception e) {
                    throw new BusinessException("Failed to serialize hint configuration: " + e.getMessage());
                }
            }
            
            Game savedGame = gameRepository.save(game);
            
            // Delete existing details if updating
            if (request.getId() != null && !request.getId().isEmpty()) {
                List<GameDetail> existingDetails = gameDetailRepository.findByGameId(savedGame.getId());
                gameDetailRepository.deleteAll(existingDetails);
            }
            
            // Save game details with hints
            if (request.getDataDetails() != null && !request.getDataDetails().isEmpty()) {
                for (GameDetailRequest detailRequest : request.getDataDetails()) {
                    saveGameDetail(detailRequest, savedGame.getId());
                }
            }
            
            return getGameById(savedGame.getId());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to save game: " + e.getMessage());
        }
    }
    
    /**
     * Save individual game detail with hints
     */
    private void saveGameDetail(GameDetailRequest detailRequest, String gameId) throws BusinessException {
        try {
            GameDetail detail = new GameDetail();
            detail.setGameId(gameId);
            detail.setX(detailRequest.getX());
            detail.setY(detailRequest.getY());
            detail.setWidth(detailRequest.getWidth());
            detail.setHeight(detailRequest.getHeight());
            detail.setD(detailRequest.getD());
            detail.setColor(detailRequest.getColor());
            detail.setMaxDiamonds(detailRequest.getMaxDiamonds());
            
            GameDetail savedDetail = gameDetailRepository.save(detail);
            
            // Save hints
            if (detailRequest.getHints() != null && !detailRequest.getHints().isEmpty()) {
                for (HintRequest hintReq : detailRequest.getHints()) {
                    GameHint hint = new GameHint();
                    hint.setGameDetailId(savedDetail.getId());
                    hint.setLevel(hintReq.getLevel());
                    hint.setText(hintReq.getText());
                    hint.setAudioUrl(hintReq.getAudioUrl());
                    hint.setAudioFileName(hintReq.getAudioFileName());
                    hint.setPointDeduction(hintReq.getPointDeduction());
                    
                    gameHintRepository.save(hint);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("Failed to save game detail: " + e.getMessage());
        }
    }
    
    /**
     * Get game by ID with all details and hints
     */
    public GameResponse getGameById(String id) throws BusinessException {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Game not found with id: " + id));
        
        return mapGameToResponse(game);
    }
    
    /**
     * Map Game entity to response DTO
     */
    private GameResponse mapGameToResponse(Game game) throws BusinessException {
        try {
            GameResponse response = modelMapper.map(game, GameResponse.class);
            
            // Parse hint configuration JSON
            if (game.getHintConfig() != null && !game.getHintConfig().isEmpty()) {
                response.setHintConfiguration(
                    objectMapper.readValue(game.getHintConfig(), java.util.Map.class)
                );
            }
            
            // Load all details with hints
            List<GameDetail> details = gameDetailRepository.findByGameId(game.getId());
            response.setDataDetails(
                details.stream()
                       .map(this::mapDetailToResponse)
                       .collect(Collectors.toList())
            );
            
            return response;
        } catch (Exception e) {
            throw new BusinessException("Failed to map game to response: " + e.getMessage());
        }
    }
    
    /**
     * Map GameDetail to response DTO with hints
     */
    private GameDetailResponse mapDetailToResponse(GameDetail detail) {
        GameDetailResponse response = modelMapper.map(detail, GameDetailResponse.class);
        
        List<GameHint> hints = gameHintRepository.findByGameDetailId(detail.getId());
        response.setHints(
            hints.stream()
                 .map(h -> modelMapper.map(h, HintResponse.class))
                 .collect(Collectors.toList())
        );
        
        return response;
    }
    
    /**
     * Get all games
     */
    public List<GameResponse> getAll() throws BusinessException {
        try {
            return gameRepository.findAll()
                    .stream()
                    .map(game -> {
                        try {
                            return mapGameToResponse(game);
                        } catch (BusinessException e) {
                            return null;
                        }
                    })
                    .filter(g -> g != null)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException("Failed to get all games: " + e.getMessage());
        }
    }
    
    /**
     * Delete game
     */
    public void delete(String id) throws BusinessException {
        try {
            if (!gameRepository.existsById(id)) {
                throw new BusinessException("Game not found with id: " + id);
            }
            gameRepository.deleteById(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to delete game: " + e.getMessage());
        }
    }
}
```

---

## ⏳ Part 6: Controller (TO CREATE)

### GameController.java
Create at: `backend/src/main/java/com/anchoi/controllers/admin/GameController.java`

```java
package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.request.GameRequest;
import com.anchoi.response.GameResponse;
import com.anchoi.response.ResponseData;
import com.anchoi.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/game")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class GameController {
    
    private final GameService gameService;
    
    /**
     * Create or update game with details and hints
     */
    @PostMapping
    public ResponseEntity<?> saveGame(@RequestBody GameRequest request) {
        try {
            GameResponse game = gameService.save(request);
            return ResponseEntity.ok(new ResponseData("Game saved successfully", 200, game));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData(e.getMessage(), 400, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData("Error saving game: " + e.getMessage(), 500, null));
        }
    }
    
    /**
     * Get game by ID with all details and hints
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getGame(@PathVariable String id) {
        try {
            GameResponse game = gameService.getGameById(id);
            return ResponseEntity.ok(new ResponseData("Game retrieved successfully", 200, game));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseData(e.getMessage(), 404, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData("Error retrieving game: " + e.getMessage(), 500, null));
        }
    }
    
    /**
     * Get all games
     */
    @GetMapping
    public ResponseEntity<?> getAllGames() {
        try {
            List<GameResponse> games = gameService.getAll();
            return ResponseEntity.ok(new ResponseData("Games retrieved successfully", 200, games));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData("Error retrieving games: " + e.getMessage(), 500, null));
        }
    }
    
    /**
     * Delete game by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable String id) {
        try {
            gameService.delete(id);
            return ResponseEntity.ok(new ResponseData("Game deleted successfully", 200, null));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData(e.getMessage(), 400, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData("Error deleting game: " + e.getMessage(), 500, null));
        }
    }
}
```

---

## ⏳ Part 7: Database Migration

### Create Migration SQL
Create file: `backend/src/main/resources/db/migration/V1__Create_Game_Tables.sql`

```sql
-- Create Game Table
CREATE TABLE IF NOT EXISTS game (
    id VARCHAR(36) PRIMARY KEY,
    image LONGTEXT,
    type INT,
    name VARCHAR(255) NOT NULL,
    max_diamonds_allowed INT DEFAULT 0,
    enable_hints BOOLEAN DEFAULT TRUE,
    hint_config JSON,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    INDEX idx_name (name),
    INDEX idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Game Detail Table
CREATE TABLE IF NOT EXISTS game_detail (
    id VARCHAR(36) PRIMARY KEY,
    game_id VARCHAR(36) NOT NULL,
    x DOUBLE,
    y DOUBLE,
    width DOUBLE,
    height DOUBLE,
    d LONGTEXT,
    color VARCHAR(50),
    max_diamonds INT DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE,
    INDEX idx_game_detail_game (game_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Game Hint Table
CREATE TABLE IF NOT EXISTS game_hint (
    id VARCHAR(36) PRIMARY KEY,
    game_detail_id VARCHAR(36) NOT NULL,
    level INT NOT NULL,
    text LONGTEXT,
    audio_url LONGTEXT,
    audio_file_name VARCHAR(255),
    point_deduction INT DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (game_detail_id) REFERENCES game_detail(id) ON DELETE CASCADE,
    UNIQUE KEY unique_detail_level (game_detail_id, level),
    INDEX idx_game_hint_detail (game_detail_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

## 📋 Implementation Checklist

### Part 1: Entities ✅
- [x] Game.java
- [x] GameDetail.java
- [x] GameHint.java

### Part 2: Repositories ✅
- [x] GameRepository.java
- [x] GameDetailRepository.java
- [x] GameHintRepository.java

### Part 3: Request DTOs ⏳
- [ ] HintRequest.java
- [ ] GameDetailRequest.java
- [ ] GameRequest.java

### Part 4: Response DTOs ⏳
- [ ] HintResponse.java
- [ ] GameDetailResponse.java
- [ ] GameResponse.java

### Part 5: Service ⏳
- [ ] GameService.java

### Part 6: Controller ⏳
- [ ] GameController.java

### Part 7: Database ⏳
- [ ] Create migration folder and SQL file

---

## 🔄 Testing with Postman

### Create Game with Hints
```
POST http://localhost:8080/api/admin/game
Content-Type: application/json

{
  "image": "<svg>...</svg>",
  "type": 1,
  "name": "Test Game",
  "maxDiamondsAllowed": 100,
  "enableHints": true,
  "hintConfiguration": {
    "enableHints": true,
    "maxHintsAllowed": 3,
    "hintLevels": [
      {"level": 1, "pointDeduction": 10, "description": "Light hint"},
      {"level": 2, "pointDeduction": 25, "description": "Medium hint"},
      {"level": 3, "pointDeduction": 50, "description": "Full hint"}
    ]
  },
  "dataDetails": [
    {
      "x": 100.5,
      "y": 200.3,
      "width": 50,
      "height": 60,
      "d": "M10 10 L 90 90",
      "color": "#FF0000",
      "maxDiamonds": 50,
      "hints": [
        {
          "level": 1,
          "text": "Look at the corner",
          "pointDeduction": 10
        }
      ]
    }
  ]
}
```

### Get Game
```
GET http://localhost:8080/api/admin/game/{gameId}
```

### Get All Games
```
GET http://localhost:8080/api/admin/game
```

### Delete Game
```
DELETE http://localhost:8080/api/admin/game/{gameId}
```

---

## ✅ Summary

All backend code has been provided:
- 3 Entities (Game, GameDetail, GameHint)
- 3 Repositories
- 3 Request DTOs
- 3 Response DTOs
- 1 Service with full CRUD
- 1 Controller with REST endpoints
- 1 Database migration SQL

**Next Steps:**
1. Copy all code files to their respective locations
2. Create database migration folder and SQL file
3. Run `mvn clean install`
4. Run application
5. Execute database migration (Flyway will run automatically)
6. Test endpoints with Postman
