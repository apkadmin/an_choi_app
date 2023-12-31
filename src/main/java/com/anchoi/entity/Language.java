package com.anchoi.entity;

import javax.persistence.*;

@Entity
@Table(name = "language")
public class language {
    @Id()
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;
}
