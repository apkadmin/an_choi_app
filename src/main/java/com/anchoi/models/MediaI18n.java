package com.anchoi.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "media_i18n")
public class MediaI18n {
    @Id
    @Column(name = "Id")
    private Integer id;

    @Column(name = "media_id")
    private String mediaId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "description")
    private String description;

}
