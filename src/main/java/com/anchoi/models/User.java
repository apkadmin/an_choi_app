package com.anchoi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Table(name = "user", schema = "an_choi_app",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class User extends BaseEntity {
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
