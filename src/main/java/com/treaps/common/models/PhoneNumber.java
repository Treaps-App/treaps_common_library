package com.treaps.common.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class PhoneNumber {
        @NotBlank(message = "Country code is mandatory")
        private String countryCode;

        private String number;
    }