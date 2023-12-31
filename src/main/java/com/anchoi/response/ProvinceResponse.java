package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;
    private String urlAudio;
    private List<I18nResponse> provinceI18ns;
    public ProvinceResponse(String id, String name, String driverCode) {
        this.id = id;
        this.name = name;
        this.driverCode = driverCode;
    }

    public ProvinceResponse() {
    }

    public ProvinceResponse(String id, String name, String squareArea, String type, String population, String density, String yearOfDensity, String coastline, String description, String latitude, String longitude, String mapImage, String driverCode, Date createdDate, String createdBy, Date updatedDate, String updatedBy, String urlAudio) {
        this.id = id;
        this.name = name;
        this.squareArea = squareArea;
        this.type = type;
        this.population = population;
        this.density = density;
        this.yearOfDensity = yearOfDensity;
        this.coastline = coastline;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mapImage = mapImage;
        this.driverCode = driverCode;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
        this.urlAudio = urlAudio;
    }
}
