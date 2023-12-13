package com.anchoi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MediaDesRequest {
   private String id;
   private String description;
   private String language;
}
