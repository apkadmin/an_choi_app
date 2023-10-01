package com.anchoi.service;

import com.anchoi.common.CommonUtils;
import com.anchoi.models.Question;
import com.anchoi.repository.QuestionRepository;
import com.anchoi.request.QuestionRequest;
import com.anchoi.response.QuestionResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionResponse> getListQuestion(){
       List<Question> questionList = questionRepository.findAll();
       return questionList.stream().map((item) -> {
          QuestionResponse response =  new QuestionResponse();
          response.setId(item.getId());
          response.setCreatedBy(item.getCreatedBy());
          response.setCreatedDate(item.getCreatedDate());
          response.setQuestions(Arrays.asList(item.getQuestion().split("#@#@#@#@")));
          response.setResults(Arrays.asList(item.getResult().split("#@#@#@#@")));
          response.setDescription(item.getDescription());
          return response;
       }).collect(Collectors.toList());
    }

    public QuestionResponse getQuestionById(String id){
        Optional<Question> item = questionRepository.findById(id);
            if(item.isPresent()) {
                QuestionResponse response = new QuestionResponse();
                response.setId(item.get().getId());
                response.setCreatedBy(item.get().getCreatedBy());
                response.setCreatedDate(item.get().getCreatedDate());
                response.setQuestions(Arrays.asList(item.get().getQuestion().split("#@#@#@#@")));
                response.setResults(Arrays.asList(item.get().getResult().split("#@#@#@#@")));
                response.setDescription(item.get().getDescription());
                return response;
            }
            return null;
    }

    public QuestionRequest updateQuestion(String id,QuestionRequest request){
        Optional<Question> item = questionRepository.findById(request.getId());
        if(item.isPresent()) {
            item.get().setDescription(request.getDescription());
            item.get().setQuestion(String.join("#@#@#@#@", request.getQuestions()));
            item.get().setResult(String.join("#@#@#@#@", request.getResults()));
            questionRepository.save(item.get());
        }
       return request;
    }
    public QuestionRequest setQuestion(QuestionRequest request){
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setDescription(request.getDescription());
        question.setQuestion(String.join("#@#@#@#@",request.getQuestions()));
        question.setResult(String.join("#@#@#@#@",request.getResults()));
        questionRepository.save(question);
        request.setId(question.getId());
       return request;
    }

    public void deleteQuestion(String id){
        questionRepository.deleteById(id);
    }
}
