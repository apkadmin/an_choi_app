package com.anchoi.response;
import com.anchoi.request.I18nRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ItemResponse {
    private String id;
    private String categoryId;
    private String districtId;
    private String provinceId;
    private String latitude;
    private String longitude;
    private String latMap;
    private String longMap;
    List<I18nResponse> itemI18ns;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;
}
