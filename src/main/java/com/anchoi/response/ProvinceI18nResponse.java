package com.anchoi.response;

import lombok.Data;

@Data
public class ProvinceI18nResponse {
    private String id;
    private String name;
    private String driverCode;

    public ProvinceI18nResponse(String id, String name, String driverCode) {
        this.id = id;
        this.name = name;
        this.driverCode = driverCode;
    }

    public ProvinceI18nResponse() {
    }
}
