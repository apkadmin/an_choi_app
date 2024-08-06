package com.anchoi.request;

import com.anchoi.entity.QuestionDetail;
import lombok.Data;

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
    private String language;

}
