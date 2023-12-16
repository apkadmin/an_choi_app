package com.anchoi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "province_i18n")
@Data
public class ProvinceI18n {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url_audio")
    private String urlAudio;


}
