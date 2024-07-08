package com.anchoi.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class DistrictRequest {
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
    @NotBlank(message = "provinceId not null")
    private String provinceId;
    private String urlAudio;
    private String squareArea;
    private List<I18nRequest> districtI18ns;

}
