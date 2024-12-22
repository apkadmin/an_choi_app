package com.anchoi.repository.question;

import com.anchoi.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> getByTypeLike(String type);

    @Query(value = "SELECT * FROM question WHERE HARD=:hard AND TYPE LIKE :type ORDER BY RAND() LIMIT :limit",nativeQuery = true)
    List<Question> getQuestion(Integer hard,String type,Integer limit);
}
