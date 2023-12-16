package com.anchoi.response;

import lombok.Data;

@Data
public class DistrictV1Response {
    private String id;
    private String name;
    private String provinceId;

    public DistrictV1Response() {
    }

    public DistrictV1Response(String id, String name, String provinceId) {
        this.id = id;
        this.name = name;
        this.provinceId = provinceId;
    }
}
