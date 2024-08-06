package com.anchoi.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private String id;
    private String icon;
    private String type;
    private String value;
    private String name;

    public CategoryResponse(String id, String icon, String type, String value, String name) {
        this.id = id;
        this.icon = icon;
        this.type = type;
        this.value = value;
        this.name = name;
    }
}
