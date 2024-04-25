package org.ahmedukamel.mulham.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.ahmedukamel.mulham.constant.RegexConstants;
import org.ahmedukamel.mulham.model.enumeration.Gender;

import java.time.LocalDate;

public record UpdateProfileRequest(
        @NotBlank @Pattern(regexp = RegexConstants.NAME, message = "{mulhm.validation.constraints.Name.message}")
        String firstName,
        @NotBlank @Pattern(regexp = RegexConstants.NAME, message = "{mulhm.validation.constraints.Name.message}")
        String lastName,
        @Pattern(regexp = RegexConstants.PHONE, message = "{mulhm.validation.constraints.Phone.message}")
        String phoneNumber,
        LocalDate dateOfBirth,
        @JsonProperty(value = "gender")
        Gender theGender) {
}
