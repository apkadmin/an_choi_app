package com.anchoi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "item_i18n")
public class ItemI18n {
    @Id
    @Column(name = "Id")
    private String id;

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

    @Basic
    @Column(name = "address")
    private String address;


}
