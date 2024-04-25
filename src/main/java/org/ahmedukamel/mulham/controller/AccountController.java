package org.ahmedukamel.mulham.controller;

import org.ahmedukamel.mulham.service.account.AccountService;
import org.ahmedukamel.mulham.service.account.IAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/p")
public class AccountController {
    private final IAccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping(value = "activate-account")
    public ResponseEntity<?> accountActivation(@RequestParam(value = "email") String email) {
        Object response = service.accountActivation(email);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "reset-password")
    public ResponseEntity<?> passwordReset(@RequestParam(value = "email") String email) {
        Object response = service.passwordReset(email);
        return ResponseEntity.ok().body(response);
    }
}