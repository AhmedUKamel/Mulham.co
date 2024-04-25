package org.ahmedukamel.mulham.handler;

import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<String> data = ex.getFieldErrors().stream().map(i -> "%s: %s".formatted(i.getField(), i.getDefaultMessage())).toList();
        return ResponseEntity.status(status).body(new ApiResponse(false, "Invalid request body.", data));
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(@NonNull NoResourceFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(new ApiResponse(false, "Not found endpoint.", ""));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(new ApiResponse(false, ex.getMessage().split(":")[0], ""));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NonNull MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(new ApiResponse(false, ex.getMessage(), ""));
    }
}