package com.anchoi.response;

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
public class GameResponse {
    private String id;
    private String image;
    private Integer type;
    private String name;
    private Integer maxDiamondsAllowed;
    private Boolean enableHints;
    private Map<String, Object> hintConfiguration;
    private List<GameDetailResponse> dataDetails;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
}