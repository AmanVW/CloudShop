package com.cloudshop.profilebs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @SequenceGenerator(name = "profile_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    private int id;

    @NotBlank(message = "First Name should not be blank or empty")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last Name should not be blank or empty")
    private String lastName;

    @NotBlank(message = "Username should not be blank or empty")
    private String username;

    @Email(message = "Email should follow 'abc@def.com' pattern")
    private String email;

    @NotBlank(message = "Phone number should not be blank or empty")
    @Pattern(regexp = "/[\\d]{10}/", message = "Phone must be of length 10 an contain only digits")
    private String phone;

    @PastOrPresent(message = "Date of birth can not be in future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @Pattern(regexp = "MALE|FEMALE|OTHER", flags = Pattern.Flag.CASE_INSENSITIVE,
    message = "Gender should only contain one of following: male, female, other")
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
