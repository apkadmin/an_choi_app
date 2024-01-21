package com.anchoi.response;

import lombok.Data;

import java.util.List;

@Data
public class ItemI18nResponse {
    private String id;
    private String categoryId;
    private String districtId;
    private String name;
    private String provinceId;
    public ItemI18nResponse(String id, String name, String categoryId, String provinceId, String districtId) {
        this.id = id;
        this.categoryId = categoryId;
        this.districtId = districtId;
        this.name = name;
        this.provinceId = provinceId;
    }

    public ItemI18nResponse() {
    }
}
