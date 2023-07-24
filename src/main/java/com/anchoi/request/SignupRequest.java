package com.anchoi.request;

import com.anchoi.models.RoleUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

@Data
@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private RoleUser role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
