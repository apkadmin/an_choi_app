package com.anchoi.response;

import lombok.Data;

import java.util.List;

@Data
public class ItemResponse {
    private String id;
    private String categoryId;
    private String districtId;
    private String latitude;
    private String longitude;
    private String latMap;
    private String longMap;
    List<I18nResponse> itemI18ns;
}
