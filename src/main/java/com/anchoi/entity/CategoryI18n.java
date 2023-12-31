package com.anchoi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "category_i18n")
public class CategoryI18n {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "name")
    private String name;
}
