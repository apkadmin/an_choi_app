package com.anchoi.request;

import lombok.*;

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

}
