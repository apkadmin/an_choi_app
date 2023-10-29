package com.anchoi.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@Data
public class Question extends BaseEntity {
    @Column(name = "description", length = 8000)
    private String description;

    @Column(name = "title", length = 1000)
    private String title;
    @Column(name = "url_image", length = 1000)
    private String urlImage;

    @Column(name = "hard")
    private String hard;

    @Column(name = "type")
    private String type;

    @Column(name = "url_audio")
    private String urlAudio;

    @OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL)
    private List<QuestionDetail> questionDetails;
}
