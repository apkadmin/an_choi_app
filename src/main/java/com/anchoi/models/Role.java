package com.anchoi.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "role", schema = "an_choi_app")
public class Role extends BaseEntity {
    @Basic
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private ERole name;

}
