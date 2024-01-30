package com.anchoi.request;

import com.anchoi.entity.ItemI18n;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ItemRequest {
    private String id;
    private String categoryId;
    private String districtId;
    private String provinceId;
    private String latitude;
    private String longitude;
    private String latMap;
    private String longMap;
    List<I18nRequest> itemI18ns;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;

}
