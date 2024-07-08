package com.anchoi.response;

import lombok.Data;

@Data
public class DistrictI18nResponse {
    private String id;
    private String name;
    private String provinceId;

    public DistrictI18nResponse() {
    }

    public DistrictI18nResponse(String id, String name, String provinceId) {
        this.id = id;
        this.name = name;
        this.provinceId = provinceId;
    }
}
