package org.ahmedukamel.mulham.handler;

import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    private ResponseEntity<?> handleRuntimeException(@NonNull RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex instanceof EntityNotFoundException) status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage(), ""), status);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    private ResponseEntity<?> handleAuthenticationException(AuthenticationException exception) {
        return new ResponseEntity<>(new ApiResponse(false, exception.getMessage(), ""), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    private ResponseEntity<?> handleBadCredentialsException() {
        return new ResponseEntity<>(new ApiResponse(false, "Wrong password", ""), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = SQLException.class)
    private ResponseEntity<?> handleEntityExistsException(@NonNull SQLException ex) {
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage(), ""), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IOException.class)
    private ResponseEntity<?> handleIOException(@NonNull IOException ex) {
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage(), ""), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}