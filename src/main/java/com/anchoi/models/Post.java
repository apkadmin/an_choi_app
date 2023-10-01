package com.anchoi.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "post")
public class Post  extends BaseEntity{
    @Column(name = "title",length = 1000)
    private String title;
    @Column(name = "body",length = 8000)
    private String body;
}
