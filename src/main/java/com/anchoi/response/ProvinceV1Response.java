package com.anchoi.response;

import lombok.Data;

@Data
public class ProvinceV1Response {
    private String id;
    private String name;
    private String driverCode;

    public ProvinceV1Response(String id, String name, String driverCode) {
        this.id = id;
        this.name = name;
        this.driverCode = driverCode;
    }

    public ProvinceV1Response() {
    }
}
