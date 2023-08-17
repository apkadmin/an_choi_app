package com.anchoi.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "category")
public class Category extends BaseEntity {
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "icon")
    private String icon;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "value")
    private String value;

}
