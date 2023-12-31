package com.anchoi.repository;

import com.anchoi.entity.QuestionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDetailRepository extends JpaRepository<QuestionDetail,String> {
    List<QuestionDetail> findAllByQuestionId(String questionId);
    void deleteAllByQuestionId(String questionId);
}
