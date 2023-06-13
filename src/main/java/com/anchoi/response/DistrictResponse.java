package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictResponse {
    private String id;
    private String name;
    private String population;
    private String squareArea;
    private String density;
    private String yearOfDensity;
    private String coastline;
    private String description;
    private String latitude;
    private String longitude;
    private String mapImage;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updatedDate;
    private String updatedBy;
    private String provinceId;
    private String provinceName;
    private String urlAudio;

}
