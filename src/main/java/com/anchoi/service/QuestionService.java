package com.anchoi.service;

import com.anchoi.common.CommonUtils;
import com.anchoi.entity.Question;
import com.anchoi.entity.QuestionDetail;
import com.anchoi.repository.question.QuestionRepository;
import com.anchoi.request.QuestionRequest;
import com.anchoi.repository.question.QuestionDetailRepository;
import com.anchoi.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionDetailRepository questionDetailRepository;

    public List<QuestionResponse> getListQuestion(){
       List<Question> questionList = questionRepository.findAll();
       return questionList.stream().map((item) -> {
          QuestionResponse response =  new QuestionResponse();
          response.setType(item.getType());
          response.setId(item.getId());
          response.setTitle(item.getTitle());
          response.setDescription(item.getDescription());
          response.setUrlAudio(item.getUrlAudio());
           response.setUrlImage(item.getUrlImage());
           response.setHard(item.getHard());
          return response;
       }).collect(Collectors.toList());
    }

    public QuestionResponse getQuestionById(String id){
        Optional<Question> item = questionRepository.findById(id);
        List<QuestionDetail> detail = questionDetailRepository.findAllByQuestionId(id);
            if(item.isPresent()) {
                QuestionResponse response = new QuestionResponse();
                response.setUrlAudio(item.get().getUrlAudio());
                response.setId(item.get().getId());
                response.setDescription(item.get().getDescription());
                response.setTitle(item.get().getTitle());
                response.setType(item.get().getType());
                response.setQuestionDetails(detail);
                response.setUrlImage(item.get().getUrlImage());
                response.setHard(item.get().getHard());
                return response;
            }
            return null;
    }

    @Transactional
    public QuestionRequest updateQuestion(String id, QuestionRequest request){
        Optional<Question> item = questionRepository.findById(id);
        questionDetailRepository.deleteAllByQuestionId(id);
        if(item.isPresent()) {
            item.get().setDescription(request.getDescription());
            item.get().setTitle(request.getTitle());
            item.get().setType(request.getType());
            item.get().setUrlImage(request.getUrlImage());
            item.get().setHard(request.getHard());
            questionRepository.save(item.get());
            request.getQuestionDetails().forEach(x -> {
                x.setQuestionId(request.getId());
                x.setId(UUID.randomUUID().toString());
            });
            questionDetailRepository.saveAll(request.getQuestionDetails());
        }
       return request;
    }
    @Transactional
    public QuestionRequest setQuestion(QuestionRequest request){
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setTitle(request.getTitle());
        question.setDescription(request.getDescription());
        question.setType(request.getType());
        question.setUrlAudio(request.getUrlAudio());
        question.setUrlImage(request.getUrlImage());
        question.setHard(request.getHard());

       Question newd = questionRepository.save(question);
        request.setId(newd.getId());
        request.getQuestionDetails().forEach(item -> {
            item.setQuestionId(newd.getId());
            item.setId(UUID.randomUUID().toString());
        });
        questionDetailRepository.saveAll(request.getQuestionDetails());
       return request;
    }

    public void deleteQuestion(String id){
        questionRepository.deleteById(id);
        questionDetailRepository.deleteAllByQuestionId(id);
    }

    public List<Question> getQuestion(String type) {
        if (type.isEmpty()) {
            type = "%%";
        } else {
            type = "%" + type + "%";
        }
        return questionRepository.getByTypeLike(type);
    }

    public List<Question> getByTypeAndHard(String type){
        if(type.isEmpty()){
            type = "%%";
        } else {
            type =  "%"+type+"%";
        }
        //Xu ly lay 2 cau rat kho => 4
        List<Question> question = new ArrayList<>();
        question.addAll(questionRepository.getQuestion(4,type, 2));

        //3 Cau Kho
        int des =5;
        des = des - question.size();
        question.addAll(questionRepository.getQuestion(3,type, des));

        //10 Cau trung binh
        des =15;
        des = des - question.size();
        question.addAll(questionRepository.getQuestion(2,type, des));

        //5 Cau de
        des =20;
        des = des - question.size();
        question.addAll(questionRepository.getQuestion(1,type, des));
         Collections.reverse(question);
         return question;
    }
}
