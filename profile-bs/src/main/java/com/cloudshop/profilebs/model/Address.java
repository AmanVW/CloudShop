package com.cloudshop.profilebs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @SequenceGenerator(name = "address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    private int id;

    private String house;

    private String street;

    private String area;

    @NotBlank(message = "City should not be blank or empty")
    private String city;

    @NotBlank(message = "State should not be blank or empty")
    private String state;

    @NotBlank(message = "Country should not be blank or empty")
    private String country;

    @NotBlank(message = "Zip code should not be blank or empty")
    @Size(min = 6, max = 6)
    private String zip;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Profile profile;
}
