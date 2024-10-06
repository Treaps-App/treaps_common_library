package com.treaps.common.auth;

import com.treaps.common.errorHandling.exception.ResourceNotFoundException;
import com.treaps.common.models.PhoneNumber;
import jakarta.validation.ValidationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuthService {

    // Extract the phone number (or username) from the Authentication object
    public static PhoneNumber getPhoneNumberFromAuth(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String[] phoneNumberArr = userDetails.getUsername().split(":"); // Assuming phone number is stored as "countryCode:number"
            if (phoneNumberArr.length == 2) {
                return new PhoneNumber(phoneNumberArr[0], phoneNumberArr[1]);
            } else {
                throw new ValidationException("Invalid phone number format");
            }
        } else if (authentication.getPrincipal() instanceof String) {
            String principal = (String) authentication.getPrincipal();
            String[] phoneNumberArr = principal.split(":"); // Assuming phone number is stored as "countryCode:number"
            if (phoneNumberArr.length == 2) {
                return new PhoneNumber(phoneNumberArr[0], phoneNumberArr[1]);
            } else {
                throw new ValidationException("Invalid phone number format");
            }
        } else {
            throw new ResourceNotFoundException("Delivery partner not found");
        }
    }

    // Extract the role from the Authentication object
    public static Optional<String> getRoleFromAuth(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities() != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                return Optional.of(authority.getAuthority()); // Return the role (e.g., ROLE_USER)
            }
        }
        return Optional.empty(); // Return empty if no role is found
    }
}
