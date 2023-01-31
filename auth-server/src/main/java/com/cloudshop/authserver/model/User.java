package com.cloudshop.authserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username should not be blank or empty")
    @Size(max = 20, message = "Username should not be longer than 20 characters")
    private String username;

    @NotBlank(message = "Email should not be blank or empty")
    @Email(message = "Email should follow 'abc@def.com' pattern")
    private String email;

    @NotBlank(message = "Password should not be blank or empty")
    @Size(min = 8, message = "Password must be atleast 8 characters long")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
