package com.anchoi.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "item_i18n")
public class ItemI18n {
    @Id
    @Column(name = "Id")
    private Integer id;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url_audio")
    private String urlAudio;


}
