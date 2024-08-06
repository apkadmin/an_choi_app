package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ItemAppResponse {
    private String id;
    private String categoryId;
    private String districtId;
    private String provinceId;
    private String latitude;
    private String longitude;
    private String latMap;
    private String longMap;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;

    private String name;
    private String description;
    private String urlAudio;
    private String address;

    public ItemAppResponse(String id, String categoryId, String districtId, String provinceId, String latitude, String longitude, String latMap, String longMap, Date createdDate, String createdBy, Date updatedDate, String updatedBy, String name, String description, String urlAudio, String address) {
        this.id = id;
        this.categoryId = categoryId;
        this.districtId = districtId;
        this.provinceId = provinceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.latMap = latMap;
        this.longMap = longMap;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
        this.name = name;
        this.description = description;
        this.urlAudio = urlAudio;
        this.address = address;
    }
}
