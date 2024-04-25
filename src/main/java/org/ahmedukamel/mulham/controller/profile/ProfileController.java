package org.ahmedukamel.mulham.controller.profile;

import jakarta.validation.Valid;
import org.ahmedukamel.mulham.dto.profile.UpdateProfileRequest;
import org.ahmedukamel.mulham.service.profile.IProfileService;
import org.ahmedukamel.mulham.service.profile.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/profile")
public class ProfileController {
    private final IProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok().body(service.getProfile());
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok().body(service.updateProfile(request));
    }

    @PutMapping(value = "picture")
    public ResponseEntity<?> setProfilePicture(@RequestParam(value = "image") MultipartFile file) throws IOException {
        return ResponseEntity.created(URI.create("api/v1/profile/picture")).body(service.setProfilePicture(file));
    }

    @DeleteMapping(value = "picture")
    public ResponseEntity<?> removeProfilePicture() throws IOException {
        service.removeProfilePicture();
        return ResponseEntity.noContent().build();
    }
}