package com.anchoi.response;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;

@Data
public class ItemResponse {
    private String id;
    private String name;
    private String categoryId;
    private String provinceId;
    private String districtId;

    public ItemResponse(String id, String name, String categoryId, String provinceId, String districtId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.provinceId = provinceId;
        this.districtId = districtId;
    }

    public ItemResponse() {
    }
}
