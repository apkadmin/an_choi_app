package com.anchoi.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "category")
public class Category extends BaseEntity {
    @Basic
    @Column(name = "icon")
    private String icon;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "value")
    private String value;

    @OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL)
    List<CategoryI18n> categoryI18ns;

}
