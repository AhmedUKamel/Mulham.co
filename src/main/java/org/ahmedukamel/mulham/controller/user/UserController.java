package org.ahmedukamel.mulham.controller.user;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.mulham.service.user.IUserService;
import org.ahmedukamel.mulham.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/user")
@PreAuthorize(value = "hasAuthority('ADMIN')")
public class UserController {
    final IUserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<?> getUser(@Min(value = 1) @PathVariable(value = "userId") Long userId) {
        return ResponseEntity.ok().body(service.getUser(userId));
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(service.getUsers());
    }

    @GetMapping(value = "page")
    public ResponseEntity<?> getUsers(@Min(value = 1) @RequestParam(value = "size") long pageSize,
                                      @Min(value = 1) @RequestParam(value = "number") long pageNumber) {
        return ResponseEntity.ok().body(service.getUsers(pageSize, pageNumber));
    }
}