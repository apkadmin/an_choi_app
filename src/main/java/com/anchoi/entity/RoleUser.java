package com.anchoi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "role_user")
public class RoleUser {
    @Id
    @Column(name = "id", nullable = true)
    private UUID id;

    @Basic
    @Column(name = "user_id", unique = true)
    private String userId;

    @Basic
    @Column(name = "object_list")
    private String objectList;

    @Basic
    @Column(name = "role_list")
    private String roleList;
}
