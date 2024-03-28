package org.ahmedukamel.mulham.dto.response;

import java.util.Collection;

public record ProfileResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String dateOfBirth,
        String gender,
        String picture,
        boolean havePicture,
        String provider,
        String role,
        Collection<String> permissions
) {
}