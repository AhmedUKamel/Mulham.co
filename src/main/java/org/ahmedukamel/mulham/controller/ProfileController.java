package org.ahmedukamel.mulham.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.ahmedukamel.mulham.dto.request.UpdateProfileRequest;
import org.ahmedukamel.mulham.service.profile.IProfileService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1/profile")
public class ProfileController {
    private final IProfileService service;

    public ProfileController(@Qualifier(value = "profileService") IProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getProfile() {
        Object response = service.getProfile();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "update")
    public ResponseEntity<Object> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        Object response = service.updateProfile(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "picture")
    public ResponseEntity<Object> setProfilePicture(@NotNull @RequestParam(value = "image") MultipartFile file) throws IOException {
        Object response = service.setProfilePicture(file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "picture")
    public ResponseEntity<Object> removeProfilePicture() throws IOException {
        Object response = service.removeProfilePicture();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}