package org.ahmedukamel.mulham.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.ahmedukamel.mulham.constant.RegexConstants;
import org.ahmedukamel.mulham.model.enumeration.Gender;

public record UpdateProfileRequest(
        @NotBlank @Email
        String email,
        @NotBlank @Pattern(regexp = RegexConstants.NAME, message = "{mulhm.validation.constraints.Name.message}")
        String firstName,
        @NotBlank @Pattern(regexp = RegexConstants.NAME, message = "{mulhm.validation.constraints.Name.message}")
        String lastName,
        @Pattern(regexp = RegexConstants.PHONE, message = "{mulhm.validation.constraints.Phone.message}")
        String phoneNumber,
        @Pattern(regexp = RegexConstants.DATE, message = "{mulhm.validation.constraints.Date.message}")
        String dateOfBirth,
        Gender theGander) {
}
