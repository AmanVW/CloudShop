package com.cloudshop.profilebs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 10)
    private String phone;

    @PastOrPresent
    @NotBlank
    private Date dateOfBirth;

    @Pattern(regexp = "MALE|FEMALE|OTHER", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String gender;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "profile_addresses",
            joinColumns = @JoinColumn(name = "pid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "aid", referencedColumnName = "id"))
    private Set<Address> addresses;
}
