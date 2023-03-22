package com.anchoi.request;

import com.anchoi.models.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;


@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private List<ERole> roles;

}
