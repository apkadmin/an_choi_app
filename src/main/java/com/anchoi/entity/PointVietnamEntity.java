package com.anchoi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "point_map")
@Data
public class PointVietnamEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "point_type")
    private String pointType;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "x")
    private Double x;

    @Column(name = "y")
    private Double y;

    @Column(name = "language")
    private String language;
}
