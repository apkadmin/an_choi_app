package com.anchoi.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "question")
@Data
public class Question extends BaseEntity {
    @Column(name = "description", length = 8000)
    private String description;

    @Column(name = "title", length = 1000)
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "url_audio")
    private String urlAudio;
}
