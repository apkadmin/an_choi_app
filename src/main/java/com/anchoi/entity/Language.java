package com.anchoi.entity;

import javax.persistence.*;

@Entity
@Table(name = "language")
public class Language {
    @Id()
    @Column(name = "id")
    private String id;

    @Basic
    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;
}
