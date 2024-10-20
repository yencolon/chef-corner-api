package org.chefcorner.chefcorner.dto.request;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
}
