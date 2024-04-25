package org.ahmedukamel.mulham.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.ahmedukamel.mulham.constant.RegexConstants;

public record RegistrationRequest(
        @NotBlank @Email
        String email,
        @NotBlank @Pattern(regexp = RegexConstants.PASSWORD, message = "{mulhm.validation.constraints.Password.message}")
        String password,
        @NotBlank @Pattern(regexp = RegexConstants.NAME, message = "{mulhm.validation.constraints.Name.message}")
        String firstName,
        @NotBlank @Pattern(regexp = RegexConstants.NAME, message = "{mulhm.validation.constraints.Name.message}")
        String lastName,
        String deviceToken) {
}