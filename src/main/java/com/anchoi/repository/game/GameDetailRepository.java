package com.anchoi.repository.game;

import com.anchoi.entity.GameDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameDetailRepository extends JpaRepository<GameDetail, String> {
    List<GameDetail> findByGameId(String gameId);
}