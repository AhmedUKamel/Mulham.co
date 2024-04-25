package org.ahmedukamel.mulham.dto.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServiceDto(
        int id,

        @NotBlank
        String englishName,

        @NotBlank
        String arabicName,

        @NotBlank
        String englishDescription,

        @NotBlank
        String arabicDescription,

        @NotNull
        @Min(value = 0)
        BigDecimal cost,

        @NotNull
        @Min(value = 1)
        Integer categoryId) {
}