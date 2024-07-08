package com.anchoi.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "JigsawDetail")
@Data
public class JigsawDetailEntity extends BaseEntity {
    @Column(name = "parentId")
    private String parentId;
    @Column(name = "width")
    private BigDecimal width;
    @Column(name = "height")
    private BigDecimal height;
    @Column(name = "x")
    private BigDecimal x;
    @Column(name = "y")
    private BigDecimal y;
    @Column(name = "d", length = 9999)
    private String d;
    @Column(name = "color")
    private String color;
}
