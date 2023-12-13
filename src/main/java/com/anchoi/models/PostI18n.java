package com.anchoi.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "post_i18n")
public class PostI18n {
    @Id
    @Column(name = "Id")
    private Integer id;

    @Column(name = "post_id")
    private String postId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "body")
    private String body;

    @Column(name = "name")
    private String name;
}
