package org.ahmedukamel.mulham.controller;

import org.ahmedukamel.mulham.service.user.IUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1")
public class UserController {
    final IUserService service;

    public UserController(@Qualifier(value = "userService") IUserService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAuthority('user:read')")
    @GetMapping(value = "u/{userId}")
    public ResponseEntity<Object> getUserProfile(@PathVariable(value = "userId") Long userId) {
        Object response = service.getUserProfile(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}