package com.anchoi.repository.game;

import com.anchoi.entity.GameHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameHintRepository extends JpaRepository<GameHint, String> {
    List<GameHint> findByGameDetailId(String gameDetailId);
    List<GameHint> findByGameDetailIdOrderByLevelAsc(String gameDetailId);
}