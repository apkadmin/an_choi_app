package com.anchoi.controllers.admin;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.Question;
import com.anchoi.request.QuestionRequest;
import com.anchoi.response.QuestionResponse;
import com.anchoi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllPosts() {
        return ResponseEntity.ok().body(questionService.getListQuestion());
    }
    @GetMapping("/get-question")
    public ResponseEntity<List<Question>> getQuestionByType(@RequestParam()  String type) {
        return ResponseEntity.ok().body(questionService.getByTypeAndHard(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable String id) {
        return ResponseEntity.ok().body(questionService.getQuestionById(id));
    }

    @PostMapping
    public ResponseEntity<QuestionRequest> createQuestion(@RequestBody QuestionRequest question) {
        return ResponseEntity.ok().body(questionService.setQuestion(question));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionRequest> updatePost(@PathVariable String id, @RequestBody QuestionRequest updateQuestion) throws BusinessException {
        return ResponseEntity.ok().body(questionService.updateQuestion(id, updateQuestion));
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
    }
}