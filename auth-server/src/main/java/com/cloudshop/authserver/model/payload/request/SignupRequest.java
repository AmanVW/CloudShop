package com.cloudshop.authserver.model.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
    private Set<String> roles;
}
