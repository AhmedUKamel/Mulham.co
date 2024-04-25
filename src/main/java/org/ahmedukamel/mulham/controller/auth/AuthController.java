package org.ahmedukamel.mulham.controller.auth;

import jakarta.validation.Valid;
import org.ahmedukamel.mulham.dto.auth.*;
import org.ahmedukamel.mulham.service.auth.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    private final IAuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest requestBody) {
        return ResponseEntity.ok().body(service.login(requestBody));
    }

    @PostMapping(value = "logout")
    public ResponseEntity<?> logout(@RequestParam(value = "access_token") String accessToken,
                                    @RequestParam(name = "device_token", required = false) String deviceToken) {
        service.logout(accessToken, deviceToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest requestBody) {
        return ResponseEntity.created(URI.create("/api/v1/auth/register")).body(service.register(requestBody));
    }
}