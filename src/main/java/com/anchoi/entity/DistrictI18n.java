package com.anchoi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "district_i18n")
public class DistrictI18n {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "district_id")
    private String districtId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url_audio")
    private String urlAudio;


}
