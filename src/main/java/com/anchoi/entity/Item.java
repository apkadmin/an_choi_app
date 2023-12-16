package com.anchoi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item extends BaseEntity {
    @Basic
    @Column(name = "category_id")
    private String categoryId;
    @Basic
    @Column(name = "province_id")
    private String provinceId;
    @Basic
    @Column(name = "district_id")
    private String districtId;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "latitude")
    private String latitude;
    @Basic
    @Column(name = "longitude")
    private String longitude;

    @Basic
    @Column(name = "lat_map")
    private String latMap;
    @Basic
    @Column(name = "long_map")
    private String longMap;

    @OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL)
    List<ItemI18n> itemI18ns;

}
