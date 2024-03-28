package org.ahmedukamel.mulham.controller;

import jakarta.validation.Valid;
import org.ahmedukamel.mulham.dto.request.LoginRequest;
import org.ahmedukamel.mulham.dto.request.RegistrationRequest;
import org.ahmedukamel.mulham.service.auth.IAuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    private final IAuthService service;

    public AuthController(@Qualifier(value = "authService") IAuthService service) {
        this.service = service;
    }

    @PostMapping(value = "login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) {
        Object response = service.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegistrationRequest request) {
        Object response = service.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}