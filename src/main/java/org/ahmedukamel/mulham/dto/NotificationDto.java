package org.ahmedukamel.mulham.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ahmedukamel.mulham.model.enumeration.NotificationType;

import java.time.LocalDate;

public record NotificationDto(
        Long id,

        @NotNull
        LocalDate start,

        @NotNull
        LocalDate end,

        @NotNull
        NotificationType type,

        @NotBlank
        String englishText,

        @NotBlank
        String arabicText
) {
}