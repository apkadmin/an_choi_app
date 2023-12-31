package com.anchoi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "post")
public class Post  extends BaseEntity{
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "body")
    private String body;

    @Column(name = "name")
    private String name;
}
