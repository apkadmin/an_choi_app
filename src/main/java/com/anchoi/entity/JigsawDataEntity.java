package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "JigsawData")
public class JigsawDataEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "language")
    private String language;

    @PrePersist
    public void prePersist() {
        this.setCreatedDate(new Date());
        this.setCreatedBy("system");
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedDate(new Date());
        this.setUpdatedBy("system");
    }
    @Column(name = "image", length = 99999)
    private String image;
    @Column(name = "type")
    private Integer type;

    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL)
    private List<JigsawDetailEntity> dataDetails;
}
