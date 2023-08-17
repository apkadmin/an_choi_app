package com.anchoi.models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`media`")
public class Media extends BaseEntity {
    @Basic
    @Column(name = "`url`")
    private String url;
    @Basic
    @Column(name = "`type_media`")
    private String typeMedia;
    @Basic
    @Column(name = "`type`")
    private String type;
    @Basic
    @Column(name = "`id_refer`")
    private String idRefer;
    @Basic
    @Column(name = "`file_name`")
    private String fileName;

    @Basic
    @Column(name = "`description`", length = 1000)
    private String description;

    @Basic
    @Column(name = "`index`")
    private Integer index;
}
