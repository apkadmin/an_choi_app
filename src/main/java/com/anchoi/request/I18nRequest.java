package com.anchoi.request;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Builder
public class I18nRequest {
        private String id;
        private String languageId;
        private String name;
        private String description;
        private String urlAudio;
}
