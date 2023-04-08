package com.anchoi.models;

import com.anchoi.response.DistrictResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@SqlResultSetMapping(name = "FIND_ALL_WITH_PROVINCE_NAME",
        classes = @ConstructorResult(
                    targetClass = DistrictResponse.class,
                    columns = {
                        @ColumnResult(name = "id", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "population", type = String.class),
                        @ColumnResult(name = "density", type = String.class),
                        @ColumnResult(name = "yearOfDensity", type = String.class),
                        @ColumnResult(name = "coastline", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "latitude", type = String.class),
                        @ColumnResult(name = "longitude", type = String.class),
                        @ColumnResult(name = "mapImage", type = String.class),
                        @ColumnResult(name = "createdDate", type = Date.class),
                        @ColumnResult(name = "createdBy", type = String.class),
                        @ColumnResult(name = "updatedDate", type = Date.class),
                        @ColumnResult(name = "updatedBy", type = String.class),
                        @ColumnResult(name = "provinceId", type = String.class),
                        @ColumnResult(name = "provinceName", type = String.class),
                    }
        )
)

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "district", schema = "an_choi_app", catalog = "")
public class District extends BaseEntity {
    @Basic
    @Column(name = "name")
    private String name;
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
    @Column(name = "description")
    private String description;
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
    @Column(name = "province_id")
    private String provinceId;
    @Basic
    @Column(name = "url_audio")
    private String urlAudio;
}
