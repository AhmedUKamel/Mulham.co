package org.ahmedukamel.mulham.handler;

import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    private ResponseEntity<ApiResponse> handleRuntimeException(@NonNull RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex instanceof AuthenticationException || ex instanceof AccessDeniedException) status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage(), null), status);
    }

    @ExceptionHandler(value = SQLException.class)
    private ResponseEntity<ApiResponse> handleEntityExistsException(@NonNull SQLException ex) {
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IOException.class)
    private ResponseEntity<ApiResponse> handleIOException(@NonNull IOException ex) {
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}