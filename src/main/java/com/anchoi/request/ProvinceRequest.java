package com.anchoi.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ProvinceRequest {
    private String id;
    @NotBlank
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
