package com.anchoi.models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media", schema = "an_choi_app")
public class Media extends BaseEntity {
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "type_media")
    private String typeMedia;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "id_refer")
    private String idRefer;

}
