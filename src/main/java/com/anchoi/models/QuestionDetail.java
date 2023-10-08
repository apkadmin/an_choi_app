package com.anchoi.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "question_detail")
@Data
public class QuestionDetail extends BaseEntity {
    @Column(name = "question", length = 8000)
    private String question;
    @Column(name = "result")
    private Boolean result;
    @Column(name = "questionId")
    private String questionId;
}
