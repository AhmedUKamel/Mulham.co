package org.ahmedukamel.mulham.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
        int id,

        @NotBlank
        String englishName,

        @NotBlank
        String arabicName,

        @NotBlank
        String icon) {
}