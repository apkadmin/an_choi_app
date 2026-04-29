package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.request.GameRequest;
import com.anchoi.response.GameResponse;
import com.anchoi.response.GameListResponse;
import com.anchoi.response.ResponseData;
import com.anchoi.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    /**
     * Create or update a game with details and hints
     * POST /api/admin/game
     * 
     * @throws BusinessException
     */
    @PostMapping()
    public ResponseEntity<ResponseData<GameResponse>> saveGame(@RequestBody GameRequest request)
            throws BusinessException {
        GameResponse response = gameService.save(request);
        return ResponseEntity.ok(ResponseData.ok(response));
    }

    /**
     * Get a game by ID with all details and hints
     * GET /api/admin/game/{id}
     * 
     * @throws BusinessException
     */
    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGameById(@PathVariable String id) throws BusinessException {
        return ResponseEntity.ok(gameService.getGameById(id));

    }

    /**
     * Get all games (detailed list with nested data)
     * GET /api/admin/game
     * 
     * @throws BusinessException
     */
    @GetMapping()
    public ResponseEntity<List<GameListResponse>> getAllGames() throws BusinessException {

        return ResponseEntity.ok(gameService.getAllSimple());

    }

    /**
     * Get all games (simplified list without details)
     * GET /api/admin/game/list/simple
     * 
     * @throws BusinessException
     */
    @GetMapping("/list/simple")
    public ResponseEntity<ResponseData<List<GameListResponse>>> getAllGamesSimple() throws BusinessException {
        List<GameListResponse> response = gameService.getAllSimple();
        return ResponseEntity.ok(ResponseData.ok(
                response));

    }

    /**
     * Delete a game by ID
     * DELETE /api/admin/game/{id}
     * 
     * @throws BusinessException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<String>> deleteGame(@PathVariable String id) throws BusinessException {

        gameService.delete(id);
        return ResponseEntity.ok(ResponseData.ok(
                "Game deleted successfully"));

    }
}
