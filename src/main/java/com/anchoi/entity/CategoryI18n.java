package com.anchoi.entity;

import javax.persistence.Column;
import javax.persistence.Id;

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
