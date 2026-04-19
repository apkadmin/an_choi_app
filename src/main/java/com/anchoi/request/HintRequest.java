package com.anchoi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HintRequest {
    private Integer level;
    private String text;
    private String audioUrl;
    private String audioFileName;
    private Integer pointDeduction;
}