package org.ahmedukamel.mulham.handler;

import lombok.AllArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@AllArgsConstructor
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<String> data = ex.getFieldErrors().stream().map(i -> String.format("%s: %s", i.getField(), i.getDefaultMessage())).toList();
        String message = messageSource.getMessage("MethodArgumentNotValidException.message", null, Locale.ENGLISH);
        return new ResponseEntity<>(new ApiResponse(false, message, data), status);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(@NonNull NoResourceFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        String message = messageSource.getMessage("NoResourceFoundException.message", null, Locale.ENGLISH);
        return new ResponseEntity<>(new ApiResponse(false, message, null), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage().split(":")[0], null), status);
    }
}