package com.anchoi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
public class DistrictResponse {
    private String id;
    private String population;
    private String density;
    private String yearOfDensity;
    private String coastline;
    private String latitude;
    private String longitude;
    private String mapImage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;
    private String provinceId;
    private String provinceName;
    private String squareArea;
    private List<I18nResponse> districtI18ns;
}
