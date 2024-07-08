package com.anchoi.repository.game;

import com.anchoi.entity.GameUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameUserRepository extends JpaRepository<GameUser, String> {
    @Query(value = "SELECT * FROM GAME_USER gu WHERE gu.game_id = :gameId " +
            "GROUP BY gu.user_id, gu.id, gu.time_count, gu.created_date " +
            "ORDER BY MIN(gu.time_count) ASC LIMIT 3", nativeQuery = true)
    List<GameUser> findTop3ByGameIdOrderByTimeCountAsc(@Param("gameId") String gameId);

    @Query(value = "SELECT * FROM GAME_USER gu WHERE gu.game_id = :gameId AND gu.user_id = :userId " +
            "ORDER BY gu.time_count ASC LIMIT 3", nativeQuery = true)
    List<GameUser> findTop3ByUserIdAndGameIdOrderByTimeCountAsc(@Param("gameId") String gameId, @Param("userId") String userId);


}