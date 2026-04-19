package com.anchoi.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {
    private String id;
    private String image;
    private Integer type;
    private String name;
    private Integer maxDiamondsAllowed;
    private Boolean enableHints;
    private Map<String, Object> hintConfiguration;
    private List<GameDetailRequest> dataDetails;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String updatedBy;
}