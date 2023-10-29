package com.anchoi.request;

import com.anchoi.models.Question;
import com.anchoi.models.QuestionDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionRequest {
    private String id;
    private String urlAudio;
    private String type;
    private String description;
    private String title;
    private String hard;
    private String urlImage;
    private List<QuestionDetail> questionDetails;


}
