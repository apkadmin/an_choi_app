package com.anchoi.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDetailRequest {
    private String id;
    private String gameId;
    private Double x;
    private Double y;
    private Double width;
    private Double height;
    private String d;
    private String color;
    private Integer maxDiamonds;
    private String transform;
    private String type;
    private List<HintRequest> hints;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;
}