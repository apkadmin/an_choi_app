package com.anchoi.response;

import lombok.*;

public class SearchResponse {
    private String id;
    private String name;
    private String type;
    private String subType;
    private String provinceId;

    public SearchResponse(String id, String name, String type, String subType, String provinceId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.subType = subType;
        this.provinceId = provinceId;
    }

    public SearchResponse() {
    }

    public SearchResponse(String id, String name, String type, String subType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.subType = subType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
