package com.anchoi.service;

import com.anchoi.entity.GameUser;
import com.anchoi.entity.UserClient;
import com.anchoi.repository.game.GameUserRepository;
import com.anchoi.repository.game.UserClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameUserService {
    private final GameUserRepository repository;
    private  final UserClientRepository userClientRepository;

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

    public UserClient createUser(UserClient userClient) {
        Optional<UserClient> exist = userClientRepository.findById(userClient.getUserId());
        if(exist.isEmpty()) {
            userClient.setCreatedDate(new Date());
            userClient.setUpdatedDate(new Date());
            return userClientRepository.save(userClient);

        }
        return exist.get();
    }


    public UserClient updateUserName(String userId, String userName) {
        Optional<UserClient> client =  userClientRepository.findById(userId);
        if (client.isPresent()) {
            client.get().setUsername(userName);
             userClientRepository.save(client.get());
             return  client.get();
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