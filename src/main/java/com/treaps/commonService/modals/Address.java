package com.treaps.commonService.modals;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String addressLine1;

    @NotBlank(message = "Address name is mandatory")
    private String addressLine2;

    @NotNull(message = "Latitude is mandatory")
    private Double latitude;

    @NotNull(message = "Longitude is mandatory")
    private Double longitude;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;
    @NotBlank(message = "postalCode is mandatory")
    private String postalCode;

    @NotBlank(message = "Country is mandatory")
    private String country;
}