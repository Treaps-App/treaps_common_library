package com.treaps.common.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

public class AuthenticationUtils {

    // Extract the phone number (or username) from the Authentication object
    public static String getPhoneNumberFromAuth(Authentication authentication) {
        return authentication != null ? authentication.getName() : null;
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
