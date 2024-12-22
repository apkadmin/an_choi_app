package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "itemId", cascade = CascadeType.ALL)
    List<ItemI18n> itemI18ns;

}
