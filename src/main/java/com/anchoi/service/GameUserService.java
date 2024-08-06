package com.anchoi.service;

import com.anchoi.entity.GameUser;
import com.anchoi.repository.game.GameUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameUserService {
    @Autowired
    private GameUserRepository repository;

    public List<GameUser> getAllGameUsers() {
        return repository.findAll();
    }

    public Optional<GameUser> getGameUserById(String id) {
        return repository.findById(id);
    }

    public GameUser createGameUser(GameUser gameUser) {
        gameUser.setId(UUID.randomUUID().toString());
        gameUser.setCreatedDate(new Date());
        return repository.save(gameUser);
    }

    public GameUser updateGameUser(String id, GameUser gameUser) {
        if (repository.existsById(id)) {
            gameUser.setId(id);
            return repository.save(gameUser);
        } else {
            throw new RuntimeException("GameUser not found");
        }
    }

    public void deleteGameUser(String id) {
        repository.deleteById(id);
    }

    public List<GameUser> getTop3ByGameId(String gameId) {
        return repository.findTop3ByGameIdOrderByTimeCountAsc(gameId);
    }

    public List<GameUser> getTop3ByUserIdAndGameId(String gameId, String userId) {
        return repository.findTop3ByUserIdAndGameIdOrderByTimeCountAsc(gameId, userId);
    }
}