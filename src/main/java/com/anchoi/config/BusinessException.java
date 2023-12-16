package com.anchoi.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"stackTrace","cause", "message", "suppressed", "localizedMessage"})
public class BusinessException extends Exception {
    private String code;
    private String desc;
}
