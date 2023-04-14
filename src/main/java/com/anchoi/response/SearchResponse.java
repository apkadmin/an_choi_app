package com.anchoi.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private String id;
    private String name;
    private String type;
    private String subType;
}
