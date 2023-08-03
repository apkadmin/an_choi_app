package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class DistrictResponse {
    private String id;
    private String name;
    private String population;
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
    private String squareArea;

    public DistrictResponse(String id, String name, String population, String density, String yearOfDensity, String coastline, String description, String latitude, String longitude, String mapImage, Date createdDate, String createdBy, Date updatedDate, String updatedBy, String provinceId, String provinceName, String urlAudio, String squareArea) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.density = density;
        this.yearOfDensity = yearOfDensity;
        this.coastline = coastline;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mapImage = mapImage;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.urlAudio = urlAudio;
        this.squareArea = squareArea;
    }

    public DistrictResponse(String id, String name, String provinceId) {
        this.id = id;
        this.name = name;
        this.provinceId = provinceId;
    }

    public DistrictResponse() {
    }
}
