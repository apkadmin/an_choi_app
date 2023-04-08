package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProvinceResponse {
    private String id;
    private String name;
    private String squareArea;
    private String type;
    private String population;
    private String density;
    private String yearOfDensity;
    private String coastline;
    private String description;
    private String latitude;
    private String longitude;
    private String mapImage;
    private String driverCode;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updatedDate;
    private String updatedBy;
    private String urlAudio;
}
