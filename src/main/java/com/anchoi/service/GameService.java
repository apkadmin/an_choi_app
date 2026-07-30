package com.anchoi.service;

import com.anchoi.common.CommonUtils;
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
import com.anchoi.response.GameListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    
    private final GameRepository gameRepository;
    private final GameDetailRepository gameDetailRepository;
    private final GameHintRepository gameHintRepository;
    private final ObjectMapper objectMapper;
    
    /**
     * Create or update game with details and hints
     */
    public GameResponse save(GameRequest request) throws BusinessException {
        try {
            Game game = new Game();
            boolean isUpdate = false;
            
            // If ID exists, try to load existing game for update; otherwise create with provided ID
            if (request.getId() != null && !request.getId().isEmpty()) {
                var existingGame = gameRepository.findById(request.getId()).orElse(null);
                if (existingGame != null) {
                    game = existingGame;
                    isUpdate = true;
                    log.info("✏️ Updating existing Game with ID: {}", request.getId());
                } else {
                    game.setId(request.getId());
                    log.info("📝 Creating new Game with provided ID: {}", request.getId());
                }
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
                    throw new BusinessException("500", "Failed to serialize hint configuration: " + e.getMessage());
                }
            }
            
            Game savedGame = gameRepository.save(game);
            log.info("✅ Saved Game with ID: {}", savedGame.getId());
            
            // Delete existing details and their hints if updating
            if (isUpdate) {
                List<GameDetail> existingDetails = gameDetailRepository.findByGameId(savedGame.getId());
                log.info("🗑️ Deleting {} existing GameDetails for Game: {}", existingDetails.size(), savedGame.getId());
                
                // Explicitly delete all hints first to ensure cascade works properly
                for (GameDetail detail : existingDetails) {
                    List<GameHint> hints = gameHintRepository.findByGameDetailId(detail.getId());
                    if (!hints.isEmpty()) {
                        log.info("🗑️ Deleting {} hints for GameDetail: {}", hints.size(), detail.getId());
                        gameHintRepository.deleteAll(hints);
                    }
                }
                
                gameDetailRepository.deleteAll(existingDetails);
                log.info("✅ All GameDetails and hints deleted successfully");
            }
            
            // Save game details with hints
            if (request.getDataDetails() != null && !request.getDataDetails().isEmpty()) {
                log.info("📥 Processing {} GameDetails for Game: {}", request.getDataDetails().size(), savedGame.getId());
                for (GameDetailRequest detailRequest : request.getDataDetails()) {
                    saveGameDetail(detailRequest, savedGame.getId());
                }
            }
            
            return getGameById(savedGame.getId());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("500", "Failed to save game: " + e.getMessage());
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
            detail.setType(detailRequest.getType());
            detail.setTransform(detailRequest.getTransform());
            
            GameDetail savedDetail = gameDetailRepository.save(detail);
            log.info("✅ Saved GameDetail with ID: {} for Game: {}", savedDetail.getId(), gameId);
            
            // Save hints
            if (detailRequest.getHints() != null && !detailRequest.getHints().isEmpty()) {
                log.info("📝 Saving {} hints for GameDetail: {}", detailRequest.getHints().size(), savedDetail.getId());
                
                for (HintRequest hintReq : detailRequest.getHints()) {
                    log.debug("💾 Processing hint - Level: {}, Text length: {}", hintReq.getLevel(), hintReq.getText() != null ? hintReq.getText().length() : 0);
                    
                    if (hintReq.getLevel() == null) {
                        log.warn("⚠️ Hint level is null, skipping hint for GameDetail: {}", savedDetail.getId());
                        continue;
                    }
                    
                    GameHint hint = new GameHint();
                    hint.setGameDetailId(savedDetail.getId());
                    hint.setLevel(hintReq.getLevel());
                    hint.setText(hintReq.getText());
                    hint.setAudioUrl(hintReq.getAudioUrl());
                    hint.setAudioFileName(hintReq.getAudioFileName());
                    hint.setPointDeduction(hintReq.getPointDeduction());
                    
                    try {
                        GameHint savedHint = gameHintRepository.save(hint);
                        log.info("✅ Saved GameHint with ID: {} (Level: {}) for GameDetail: {}", 
                                savedHint.getId(), savedHint.getLevel(), savedDetail.getId());
                    } catch (Exception hintException) {
                        log.error("❌ Failed to save hint level {}: {}", hintReq.getLevel(), hintException.getMessage(), hintException);
                        throw hintException;
                    }
                }
                log.info("✅ All {} hints saved successfully for GameDetail: {}", detailRequest.getHints().size(), savedDetail.getId());
            } else {
                log.info("ℹ️ No hints provided for GameDetail: {}", savedDetail.getId());
            }
        } catch (Exception e) {
            log.error("❌ Exception in saveGameDetail: {}", e.getMessage(), e);
            throw new BusinessException("500", "Failed to save game detail: " + e.getMessage());
        }
    }

    
    /**
     * Get game by ID with all details and hints
     */
    public GameResponse getGameById(String id) throws BusinessException {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new BusinessException("400", "Game not found with id: " + id));
        
        return mapGameToResponse(game);
    }
    
    /**
     * Map Game entity to response DTO
     */
    private GameResponse mapGameToResponse(Game game) throws BusinessException {
        try {
            GameResponse response = CommonUtils.toObject(game, GameResponse.class);
            
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
            throw new BusinessException("500", "Failed to map game to response: " + e.getMessage());
        }
    }
    
    /**
     * Map GameDetail to response DTO with hints
     */
    private GameDetailResponse mapDetailToResponse(GameDetail detail) {
        GameDetailResponse response = CommonUtils.toObject(detail, GameDetailResponse.class);
        
        List<GameHint> hints = gameHintRepository.findByGameDetailId(detail.getId());
        response.setHints(
            hints.stream()
                 .map(h -> CommonUtils.toObject(h, HintResponse.class))
                 .collect(Collectors.toList())
        );
        
        return response;
    }
    
    /**
     * Get all games
     */
    public List<GameResponse> getAll() throws BusinessException {
        try {
            log.info("📥 Fetching all games");
            List<GameResponse> games = gameRepository.findAll()
                    .stream()
                    .map(game -> {
                        try {
                            return mapGameToResponse(game);
                        } catch (BusinessException e) {
                            log.error("❌ Error mapping game {}: {}", game.getId(), e.getMessage());
                            return null;
                        }
                    })
                    .filter(g -> g != null)
                    .collect(Collectors.toList());
            log.info("✅ Retrieved {} games", games.size());
            return games;
        } catch (Exception e) {
            log.error("❌ Error getting all games: {}", e.getMessage(), e);
            throw new BusinessException("500", "Failed to get all games: " + e.getMessage());
        }
    }
    
    /**
     * Get all games - simplified list without details
     */
    public List<GameListResponse> getAllSimple() throws BusinessException {
        try {
            log.info("📥 Fetching all games (simplified)");
            List<GameListResponse> games = gameRepository.findAll()
                    .stream()
                    .map(game -> {
                        GameListResponse response = new GameListResponse();
                        response.setId(game.getId());
                        response.setCreatedDate(game.getCreatedDate());
                        response.setCreatedBy(game.getCreatedBy());
                        response.setUpdatedDate(game.getUpdatedDate());
                        response.setUpdatedBy(game.getUpdatedBy());
                        response.setImage(game.getImage());
                        response.setType(game.getType());
                        response.setName(game.getName());
                        return response;
                    })
                    .collect(Collectors.toList());
            log.info("✅ Retrieved {} games (simplified)", games.size());
            return games;
        } catch (Exception e) {
            log.error("❌ Error getting all games: {}", e.getMessage(), e);
            throw new BusinessException("500", "Failed to get all games: " + e.getMessage());
        }
    }
    
    /**
     * Delete game
     */
    public void delete(String id) throws BusinessException {
        try {
            log.info("🗑️ Deleting game with ID: {}", id);
            
            if (!gameRepository.existsById(id)) {
                throw new BusinessException("400", "Game not found with id: " + id);
            }
            
            // Load and delete all details and hints
            List<GameDetail> details = gameDetailRepository.findByGameId(id);
            log.info("🗑️ Found {} GameDetails for Game: {}", details.size(), id);
            
            for (GameDetail detail : details) {
                List<GameHint> hints = gameHintRepository.findByGameDetailId(detail.getId());
                if (!hints.isEmpty()) {
                    log.info("🗑️ Deleting {} hints for GameDetail: {}", hints.size(), detail.getId());
                    gameHintRepository.deleteAll(hints);
                }
            }
            
            if (!details.isEmpty()) {
                gameDetailRepository.deleteAll(details);
            }
            
            gameRepository.deleteById(id);
            log.info("✅ Game and all related data deleted successfully: {}", id);
        } catch (BusinessException e) {
            log.error("❌ BusinessException deleting game: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("❌ Error deleting game {}: {}", id, e.getMessage(), e);
            throw new BusinessException("500", "Failed to delete game: " + e.getMessage());
        }
    }
}