package org.ahmedukamel.mulham.controller;

import org.ahmedukamel.mulham.service.image.IImageViewerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "p")
public class ImageViewerController {
    final IImageViewerService service;

    public ImageViewerController(@Qualifier(value = "imageViewerService") IImageViewerService service) {
        this.service = service;
    }

    @GetMapping(value = "image/{imageName}")
    public ResponseEntity<Object> getProfilePicture(@PathVariable(value = "imageName") String imageName) throws IOException {
        Object response = service.getProfilePicture(imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(response);
    }

    @GetMapping(value = "logo")
    public ResponseEntity<Object> getLogo() throws IOException {
        Object response = service.getLogo();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(response);
    }
}