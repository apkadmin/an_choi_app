package com.anchoi.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class I18nResponse {
        private String id;
        private String languageId;
        private String name;
        private String description;
        private String urlAudio;
}
