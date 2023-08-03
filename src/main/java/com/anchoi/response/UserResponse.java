package com.anchoi.response;

import com.anchoi.models.RoleUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.management.relation.Role;
import java.util.Date;
import java.util.List;


@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"password"})
public class UserResponse {
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;
    private String updatedBy;

    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private RoleUser role;

}
