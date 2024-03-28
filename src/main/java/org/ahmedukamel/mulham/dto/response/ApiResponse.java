package org.ahmedukamel.mulham.dto.response;

public record ApiResponse(
        boolean success,
        String message,
        Object data
) {
}
