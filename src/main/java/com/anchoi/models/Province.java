package com.anchoi.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "province")
public class Province extends BaseEntity {

    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "square_area")
    private String squareArea;
    @Basic
    @Column(name = "population")
    private String population;
    @Basic
    @Column(name = "density")
    private String density;
    @Basic
    @Column(name = "year_of_density")
    private String yearOfDensity;
    @Basic
    @Column(name = "coastline")
    private String coastline;
    @Basic
    @Column(name = "latitude")
    private String latitude;
    @Basic
    @Column(name = "longitude")
    private String longitude;
    @Basic
    @Column(name = "map_image")
    private String mapImage;
    @Basic
    @Column(name = "driver_code")
    private String driverCode;

    @OneToMany(mappedBy = "provinceId", cascade = CascadeType.ALL)
    List<ProvinceI18n> provinceI18ns;
}
